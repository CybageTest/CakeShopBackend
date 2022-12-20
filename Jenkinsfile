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
                    bat 'docker login -u iamabhijeetgurav -p Sunilam@123'
                    bat 'docker push iamabhijeetgurav/cybagetest:cakeshopfinal'
                }
            }
        }
        stage('deploy on kubernetes') {
            steps {
                script {
                    kubeconfig(caCertificate: '''-----BEGIN CERTIFICATE-----
MIIDBjCCAe6gAwIBAgIBATANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwptaW5p
a3ViZUNBMB4XDTIyMTIwODA2Mzk1MloXDTMyMTIwNjA2Mzk1MlowFTETMBEGA1UE
AxMKbWluaWt1YmVDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKmg
gi6ucPTa0NpfQfuedgcE11sOnP1Kv/n9EEMCI8uUATSXZ3v2pWjlRuV1RPgyoH73
jmcv43b8WKX1HO5tjRSH9RFKG2pMCq7Bty+tHtF/64XQB3YVSHIuyAYkud1C9XC6
W94iL3ylSLHlAmLLRQ/bgWfUx6gEtkEMP0bKapwqpbZGAl9kg3PJOSaJZ5pDDdvv
wJ1OJ3GwwghoUf9FGR7pv77NRu0GlXOme8NfxVqF96FrOJNPg6f3Nbbl2hvGKgxb
Q0epCRyTBy0N6re+hfl01BD7M6SKheKwpC/UYq9TF/L+mKtnf4JoRd45Erj3TVis
mjzXWdBy10nYlT7FyQUCAwEAAaNhMF8wDgYDVR0PAQH/BAQDAgKkMB0GA1UdJQQW
MBQGCCsGAQUFBwMCBggrBgEFBQcDATAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQW
BBQbUTkJ3+dRAsmhSUd/wjbBiKTytzANBgkqhkiG9w0BAQsFAAOCAQEAF7upG6zX
gvYgHk5LdHsSxlrQPwOdviL4C8GYXeUXL+/YkbpB/gwVgfMgq9YeIaHm3txdo1IQ
FIdsjd91Vzwp3vimqhetnRqhbCGWRNpgvROAEJ33zFwxnJDHjoyCrAehKnn9lljy
AaG04BQsOxFUEop/iD6yvcaI2eCzMyI+r7LaKFVsKLmOVlRNyUDc4zFxVxsVSRYt
qpqSHmF51hRiGE+zOcRDbzs+PO3ILm1MM7zXT33UkdGURCbNvED6dawOhTroyytQ
H6qOlHVKpiRQzODPZquojPo28bkc15TT7pROzhv+ntCDq3qzvSfU6Mq8lerEqzUy
biD0IJRKtRVgew==
-----END CERTIFICATE-----''', serverUrl: '192.168.49.2:8443') {
                        bat 'kubectl apply -f deploymentservice.yml'
}
                }
            }
        }
    }
}
