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
                    bat 'docker build -t cybagetest/cakeshopfinal .'
                }
            }
        }
        stage('Push docker image to hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'docker-hub-pwd', variable: 'docker-hub-password')]) {
                        bat 'docker login -u iamabhijeetgurav -p ${docker-hub-password}'
                    }
                    bat 'docker push cybagetest/cakeshopfinal'
                }
            }
        }
    }
}
