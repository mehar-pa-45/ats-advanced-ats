pipeline {
    agent any

    environment {
        GIT_REPO   = ""
        GIT_BRANCH = "main"

        DOCKERHUB_USER = "mehardocker45"
        IMAGE_NAME     = "ats-advanced-ats"
        IMAGE_TAG      = "latest"

        DOCKER_CREDS   = "Docker_CRED"
    }

    stages {

        stage('Checkout Code') {
            steps {
               
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('DockerHub Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDS}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                }
            }
        }

        stage('Push Image to DockerHub') {
            steps {
                sh "docker push ${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }
}
