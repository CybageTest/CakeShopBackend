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
 		stage('SonarQube Analysis'){
        	steps{
        			withSonarQubeEnv(installationName:'SonarQube', credentialsId: 'SONARQUBE_TOKEN') {
    					bat sonar-scanner	
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
