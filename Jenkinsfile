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
                    withCredentials([usernamePassword(credentialsId: 'docker_secrets', passwordVariable: 'Docker_Password', usernameVariable: 'Docker_Username')]) {
                        bat 'docker login -u ${Docker_Username} -p ${Docker_Password}'
                        bat 'docker push iamabhijeetgurav/cybagetest:cakeshopfinal'
                    }
                }
            }
        }
    }
}
