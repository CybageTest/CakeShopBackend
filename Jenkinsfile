pipeline{
    agent any
    tools{
        maven 'Maven 3.6.3'
    }
    stages{
        stage('Build Maven'){
            steps{
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    sh 'docker build -t cybagetest/cakeshopfinal .'
                }
            }
        }
    }
}