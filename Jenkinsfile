pipeline {
    agent any
    parameters {
            choice(name: 'browser', choices: ['chrome', 'safari', 'firefox'], description: 'Select browser')
            string(defaultValue: "", description: 'Add password', name: 'password')
        }
    options {
        skipDefaultCheckout(false)
        buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '5'))
    }
    triggers {
        cron('H 7 * * 1-5')
        pollSCM('* * * * 1-5')
    }
    stages {
        stage('Maven compile') {
            steps {
                withEnv(['PATH+EXTRA=/usr/sbin:/usr/bin:/sbin:/bin']) {
                    sh "mvn compile"
                }
            }
        }
        stage('Testing on production') {
                    steps {
                        withEnv(['PATH+EXTRA=/usr/sbin:/usr/bin:/sbin:/bin']) {
                            sh "mvn clean test -Dbrowser=${params.browser} -Dpassword=${params.password}"
                        }
                    }
                }
        stage('Publishing results') {
            steps {
                step([$class: 'Publisher', testResults: '**/testng-results.xml'])
            }
        }
        stage('Report generation') {
            steps {
                script {
                    echo 'Publish Allure report'
                    publishHTML(
                            target: [
                                    allowMissing          : false,
                                    alwaysLinkToLastBuild : false,
                                    keepAll               : true,
                                    reportDir             : 'target/site',
                                    reportFiles           : 'allure-maven.html',
                                    reportName            : "Allure Report"
                            ]
                    )
                }
            }
        }
        stage('Assigning build status') {
            steps {
                findText regexp                 : /(?i)ERROR.*?(Failures|Errors): [^0],/,
                        alsoCheckConsoleOutput  : true,
                        unstableIfFound         : false
           }
        }
    }
    post {
        failure {
            sendEmail('FAILED')
        }
        success {
            sendEmail("SUCCESS")
        }
    }
}

def sendEmail(buildResult) {
    mail to: 'vitalidimsf@gmail.com',
            subject: "Build ${env.JOB_NAME} - ${buildResult}",
            body: "Job \"${env.JOB_NAME}\" build#${env.BUILD_NUMBER} completed with result ${buildResult}\n\n" +
                    "Allure report:\t\t${env.BUILD_URL}Allure_20Report/\n" +
                    "Logs:\t\t\t${env.BUILD_URL}console/\n"
}