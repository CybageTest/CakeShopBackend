pipeline{
    agent any
    tools{
        maven
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