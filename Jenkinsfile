pipeline{
    agent any
    tools{
        maven 'maven_3_6_3'
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