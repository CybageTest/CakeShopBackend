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
        stage('SonarQube Analysis') {
            environment {
                    scannerHome = tool 'SonarQubeScanner'
                }
            steps {
                withSonarQubeEnv(installationName:'SonarQube', credentialsId: 'SONARQUBE_TOKEN') {
                        bat """-D sonar.projectKey=CAKESHOP_BACKEND \
                        -D sonar.language=java \
                        -D sonar.sources=my-app/src/main \
                        -D sonar.tests=my-app/src/test \
                        -D sonar.host.url=http://172.27.12.78:8000/"""
                }
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
                    bat 'docker login -u iamabhijeetgurav -p dckr_pat_hz8DzlE8E8ZkQP0BEipH0brBaQo'
                    bat 'docker push iamabhijeetgurav/cybagetest:cakeshopfinal'
                }
            }
        }
    }
}
