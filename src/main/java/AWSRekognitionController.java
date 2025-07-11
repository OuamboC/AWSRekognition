import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionAsyncClientBuilder;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

public class AWSRekognitionController {

    public static void main(String [] args){
        // Create utility class object
        AWSRekognitionUtils utils = new AWSRekognitionUtils();

        //Path to your image
        String imagePath = "src/your-profile-image.jpg";
        String outputPath ="src/your-profile-image_annotated.jpg";

        String imagePathCelebrity = "src/Brad Pitt.jpeg";
        String outputPathCelebrity = "src/Brad Pitt_annotated.jpeg";

        String imagePathDog = "src/dog.jpeg";
        String outputPathDog= "src/dog_annotated.jpg";

        String imagePathScene = "src/crowds.jpeg";
        String outputPathScene= "src/crowds_annotated.jpg";

        String imagePathClub = "src/manchester_city.jpeg";
        String outputPathClub= "src/manchester_city_annotated.jpeg";

        String imagePathObject = "src/object.jpeg";
        String outputPathObject = "src/object_annotated.jpeg";

        String imagePathPeople = "src/people.jpeg";
        String outputPathPeople = "src/people_annotated_image.jpeg";

        String imagePathTrees = "src/trees.jpeg";
        String outputPathTrees = "src/trees_annotated_image.jpeg";

        // Define your image path
        String imagePathProtest = "src/protest.jpg";


        utils.detectLabels( imagePath);
        System.out.println("---------------------------");
        utils.detectAndAnnotateFaces( imagePath, outputPath);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectLabels( imagePathCelebrity);
        utils.detectAndAnnotateFaces( imagePathCelebrity, outputPathCelebrity);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectLabels( imagePathDog);
        utils.detectAndAnnotateFaces( imagePathDog, outputPathDog);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectLabels( imagePathScene);
        utils.detectAndAnnotateFaces( imagePathScene, outputPathScene);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectLabels( imagePathClub);
        utils.detectAndAnnotateFaces( imagePathClub, outputPathClub);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectLabels( imagePathObject);
        utils.detectAndAnnotateFaces( imagePathObject, outputPathObject);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectLabelsAndAnnotateImage( imagePathPeople, outputPathPeople);
        System.out.println("---------------------------");
        utils.detectLabelsAndAnnotateImage( imagePathTrees, outputPathTrees);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        utils.detectText( imagePathProtest);


    }
}


