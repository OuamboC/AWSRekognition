# AWS Rekognition Java Application

A Java-based image analysis tool that uses **AWS Rekognition** to detect faces, labels, and annotate images. Built with **Maven**, powered by **Amazon Rekognition SDK**, and structured for clarity and scalability.

---

## 🚀 Features

- **Face Detection**: Identify and analyze faces in images with confidence scores
- **Label Detection**: Automatically detect and classify objects, scenes, and activities  
- **Image Annotation**: Generate bounding boxes around detected features
- **Local Image Processing**: Load and process images from local directories
- **Secure Configuration**: Store AWS credentials securely via `.env` file

---

## 📁 Project Structure

```
AWSRekognition/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── org/example/
│   │   │   │   └── Main.java
│   │   │   ├── AWSRekognitionController.java    
│   │   │   └── AWSRekognitionUtils.java         
│   │   └── resources/
│   │       └── [multiple test images]
│   └── test/
├── target/
├── .env
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🛠️ Setup Instructions

### Prerequisites
- **Java 23** installed
- **Maven 3.6+** for dependency management
- **AWS Account** with Rekognition service access

### 1. Clone the Repository
```bash
git clone https://github.com/OuamboC/AWSRekognition.git
cd AWSRekognition
```

### 2. Configure AWS Credentials
Create a `.env` file in the root directory:
```env
AWS_ACCESS_KEY_ID=your_access_key_here
AWS_SECRET_ACCESS_KEY=your_secret_key_here
```

**⚠️ Security Note**: The `.env` file is automatically ignored by Git to protect your credentials.

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

---

## 📦 Dependencies

The project uses the following key dependencies:

| Dependency | Purpose | Version |
|------------|---------|---------|
| AWS SDK for Java v1 (Rekognition) | Image analysis services | 1.12.114 |
| dotenv-java | Environment variable management | 3.0.0 |
| Maven | Build automation | 3.6+ |

**Complete dependency list in `pom.xml`:**
```xml
<dependencies>
    <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-java-sdk-rekognition</artifactId>
        <version>1.12.114</version>
    </dependency>
    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>dotenv-java</artifactId>
        <version>3.0.0</version>
    </dependency>
</dependencies>
```


## ⚙️ Configuration

### Environment Variables
| Variable | Description | Required |
|----------|-------------|----------|
| `AWS_ACCESS_KEY_ID` | Your AWS access key | ✅ Yes |
| `AWS_SECRET_ACCESS_KEY` | Your AWS secret key | ✅ Yes |

### AWS Permissions Required
Your AWS user needs the following permissions:
- `rekognition:DetectFaces`
- `rekognition:DetectLabels`

---

## 🚨 Troubleshooting

### Common Issues

**AWS Credentials Error**
```
Solution: Verify your .env file exists and contains valid AWS credentials
```

**"Access Denied" Error**
```
Solution: Ensure your AWS user has the required Rekognition permissions
```

**Maven Build Fails**
```
Solution: Check Java version (23 required) and run 'mvn clean compile'
```

**Dependency Issues**
```
Solution: Run 'mvn clean install' to download all dependencies
```

---

## 🧪 Testing

Run the application with a test image:
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 🔗 Resources

- [AWS Rekognition Documentation](https://docs.aws.amazon.com/rekognition/)
- [AWS SDK for Java v2 Documentation](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/)
- [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/)

---

## 👨‍💻 Author

**Your Name**
- GitHub: [@OuamboC](https://github.com/OuamboC)
- LinkedIn: [Canis Ouambo](https://www.linkedin.com/in/canis-breal-ouambo/)

---

**⭐ Star this repository if you find it helpful!**
