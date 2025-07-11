import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.List;

/*AWS Credentials and Utils Class */
/*
*AWS SDK for Java requires access to your AWS credentials. The easiest way to manage
credentials locally is by setting them up with the AWS CLI or using environment variables.
But for this project we will add the credentials to our code base.

 */
public class AWSRekognitionUtils {
    private AmazonRekognition rekognitionClient;
    public AWSRekognitionUtils() {
        Dotenv dotenv = Dotenv.load();

        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_KEY");

        //Set up AWS credentials
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        //Instantiate Rekognition client
        rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
    /* Implementing AWS Methods */
    // Utility method to load an image from file into ByteBuffer
    public static ByteBuffer loadImage(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        return ByteBuffer.wrap(imageBytes);
    }
    // Function to detect labels in the image
    public void detectLabels(String imagePath) {
        try{
            // Load image from local disk
            ByteBuffer imageBytes = loadImage(imagePath);

            // Create request for label detection
            DetectLabelsRequest detectlabelsRequest = new DetectLabelsRequest()
                    .withImage(new Image().withBytes(imageBytes)).withMaxLabels(10).withMinConfidence(75F);

            // Call Rekognition API
            DetectLabelsResult detectLabelsResponse = this.rekognitionClient.detectLabels(detectlabelsRequest);
            System.out.println(detectLabelsResponse.toString());

            //Print detected labels
            System.out.println("Detected labels: ");
            for(Label label : detectLabelsResponse.getLabels()) {
                System.out.println(label.getName() + ": " + label.getConfidence());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to detect and annotate faces on the image
    public void detectAndAnnotateFaces(String imagePath, String outputPath) {
        try{
            // Load image bytes
            ByteBuffer imageBytes = loadImage(imagePath);

            // Create a DetectFacesRequest
            DetectFacesRequest request = new  DetectFacesRequest().withImage(new Image().withBytes(imageBytes))
                    .withAttributes(Attribute.ALL); // Get all face attributes

            // Call AWS Rekognition
            DetectFacesResult result = this.rekognitionClient.detectFaces(request);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            if ( faceDetails.isEmpty()) {
                System.out.println("No faces detected.");
                return;
            }
            // Load the image into a BufferedImage object
            File inputFile = new File(imagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Get image width & height
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            // Draw bounding boxes around faces
            Graphics2D graphics = image.createGraphics();
            graphics.setStroke(new BasicStroke(3)); //Box thickness

            for ( FaceDetail face : faceDetails) {
                BoundingBox bbox = face.getBoundingBox();
                int x = (int) (bbox.getLeft() * imgWidth);
                int y = (int) (bbox.getTop() * imgHeight);
                int width = (int) (bbox.getWidth() * imgWidth);
                int height = (int) (bbox.getHeight() * imgHeight);

                // Draw the bounding box
                graphics.setColor(Color.RED); // Set box colour
                graphics.drawRect(x, y, width, height);

                graphics.setColor(Color.WHITE);

                // Add labels
                graphics.drawString(String.format("Confidence: %2f%%", face.getConfidence()), x, y -5);
                graphics.drawString(String.format("Emotion: %s", face.getEmotions().get(0).getType()), x, y - 20);

                // Save the annotated image
                File outputFile = new File(outputPath);
                ImageIO.write(image, "jpg", outputFile);
                System.out.println("Annotated image saved as: " + outputPath);

                // Release resources
                graphics.dispose();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to detect labels and annotate them on the image
    public void detectLabelsAndAnnotateImage(String imagePath, String outputPath) {
        try{
            // Load image from local disk
            ByteBuffer imageBytes = loadImage(imagePath);

            // Create request for label detection
            DetectLabelsRequest detectLabelsRequest = new DetectLabelsRequest()
                    .withImage(new Image().withBytes(imageBytes)).withMaxLabels(10).withMinConfidence(75F);

            // Call Rekognition API
            DetectLabelsResult detectLabelsResponse = this.rekognitionClient.detectLabels(detectLabelsRequest);
            System.out.println(detectLabelsResponse.toString());

            List<Label> labels = detectLabelsResponse.getLabels();
            if (labels.isEmpty()) {
                System.out.println("No faces detected.");
                return;
            }

            // Load the image into a BufferedImage object
            File inputFile = new File(imagePath);
            BufferedImage image = ImageIO.read(inputFile);

            // Get image  width and height
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            // Draw bounding boxes around faces
            Graphics2D graphics = image.createGraphics();
            graphics.setStroke(new BasicStroke(3)); // Box thickness

            for (Label label : labels) {

                 if (!label.getInstances().isEmpty()) {
                     BoundingBox bbox = label.getInstances().getFirst().getBoundingBox();

                     int x = (int) (bbox.getLeft() * imgWidth);
                     int y = (int) (bbox.getTop() * imgHeight);
                     int width = (int) (bbox.getWidth() * imgWidth);
                     int height = (int) (bbox.getHeight() * imgHeight);

                     // Draw the bounding bbox
                     graphics.setColor(Color.RED); // Set box colour
                     graphics.drawRect(x, y, width, height);
                     graphics.setColor(Color.WHITE); // Set text colour
                     graphics.drawString(String.format("%s", label.getName()), x + width / 2, y + (height /2));
                 }
            }
            File outputFile = new File(outputPath);
            ImageIO.write(image, "jpg", outputFile);
            System.out.println("Annotated image saved as: " + outputPath);

            // Release resources
            graphics.dispose();

            System.out.println("Detect labels: ");
            for ( Label label : detectLabelsResponse.getLabels()) {
                System.out.println(label.getName() + ": " + label.getConfidence());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to detect text from an image
    public void detectText( String imagePath) {
        try{
            // Load image from local disk
            ByteBuffer imageBytes = loadImage(imagePath);
            // Call AWS Rekognition for Text Detection
            DetectTextRequest request = new DetectTextRequest().withImage(new Image().withBytes(imageBytes));

            // Get Response
            DetectTextResult result = this.rekognitionClient.detectText(request);
            List<TextDetection> textDetections = result.getTextDetections();

            if (textDetections.isEmpty()) {
                System.out.println("No text detected.");
                return;
            }

            // Print Detected Text
            System.out.println("Detected Text:");
            for (TextDetection text : textDetections) {
                System.out.println(text.getDetectedText() + " (Confidence: " + text.getConfidence() + "%)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
