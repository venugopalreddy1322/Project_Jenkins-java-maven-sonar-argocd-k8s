pipeline {
  agent any
  tools{
    maven "MAVEN3"
  }
  stages {
    stage('Checkout') {
      steps {
        sh 'echo Checkout passed'
        // This case Jenkins fetches from Git as we mentioned Jenkinsfile location to this repository.
        // To get the Jenkinsfile Jenkins clones the repository. So no need to fetch again.
        //git branch: 'main', url: 'https://github.com/venugopalreddy1322/Project_Jenkins-java-maven-sonar-argocd-k8s'
      }
    }
    stage('Build and Test') {
      steps {
        
        // build the project and create a JAR file
        sh 'mvn clean package'
      }
    }
    stage('Static Code Analysis') {
      environment { 
        SONAR_URL = "http://192.168.56.13:9000/"
      }
      steps {
        
        withCredentials([string(credentialsId: 'sonarqube', variable: 'SONAR_LOCAL')]) {
          sh 'mvn sonar:sonar -Dsonar.login=${SONAR_LOCAL} -Dsonar.host.url=${SONAR_URL}'
        }
      }
    }
    stage('Build and Push Docker Image') {
      environment {
        DOCKER_IMAGE = "venu1322/ultimate-cicd:${BUILD_NUMBER}"
        //REGISTRY_CREDENTIALS = credentials('dockerhub_pwd')
      }
      steps {
        script {
            dockerImage = docker.build("${DOCKER_IMAGE}")
            //sh 'docker build -t ${DOCKER_IMAGE} .'
            //def dockerImage = docker.image("${DOCKER_IMAGE}")
            withDockerRegistry(credentialsId: 'dockerhub_pwd', url: ' https://index.docker.io/v1/') {
                dockerImage.push()
            }
        }
      }
    }
    stage('Update Deployment File') {
        environment {
            GIT_REPO_NAME = "Project_Jenkins-java-maven-sonar-argocd-k8s-final-artifact"
            GIT_USER_NAME = "venugopalreddy1322"
        }
        steps {
            withCredentials([string(credentialsId: 'github_pwd', variable: 'GITHUB_AUTH')]) {
                sh '''
                    git config user.email "venugopalreddy1322@gmail.com"
                    git config user.name "Venugopal Reddy N"
                    BUILD_NUMBER=${BUILD_NUMBER}
                    sh 'pwd'
                    sed -i "s/replaceImageTag/${BUILD_NUMBER}/g" Project_Jenkins-java-maven-sonar-argocd-k8s/k8smanifest.yaml
                    git add Project_Jenkins-java-maven-sonar-argocd-k8s/k8smanifest.yaml
                    git commit -m "Update deployment image to version ${BUILD_NUMBER}"
                    git push https://${GITHUB_AUTH}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME} HEAD:main
                '''
            }
        }
    }
  }
}
