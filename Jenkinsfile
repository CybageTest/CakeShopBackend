pipeline {
    agent any
    tools {
        maven '3.8.6'
    }
    stages {
        stage('Build Maven') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker build -t cakeshopfinal .'
                }
            }
        }
        stage('Push docker image to hub') {
            steps {
                script {
                    bat 'docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}'
                    bat 'docker push iamabhijeetgurav/cybagetest:cakeshopfinal'
                }
            }
        }
    }
}
