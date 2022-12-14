pipeline{
    agent any
    tools{
        maven 'Maven 3.8.6'
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