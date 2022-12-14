pipeline{
    agent any
    tools{
        maven '3.8.6'
    }
    stages{
        stage('Build Maven'){
            steps{
                bat 'mvn clean install'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    bat 'docker build -t cybagetest/cakeshopfinal .'
                }
            }
        }
    }
}