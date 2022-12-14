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
                    withCredentials([usernamePassword(credentialsId: '17dedfb3-3c6b-4306-94b3-3dd40aa0c2e7', passwordVariable: 'docker-password', usernameVariable: 'docker-username')]) {
                        bat 'docker login -u ${docker-username} -p ${docker-password}'                        
                    }
                    bat 'docker push cybagetest/cakeshopfinal'
                }
            }
        }
    }
}
