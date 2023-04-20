pipeline {
    agent any
    tools {
        maven '3.8.6',
        JDK 'JDK17'
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
                    bat "${scannerHome}/bin/sonar-scanner.bat"
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
