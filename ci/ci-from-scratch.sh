#!/bin/bash

set -eu

THIS_DIR="$(dirname $(readlink -f ${BASH_SOURCE[0]}))"
buildDir="${THIS_DIR}/ci-from-scratch"

info() {
    echo "INFO: $@"
}

setupGerrit() {
    local gerritUrl="https://gerrit-releases.storage.googleapis.com/gerrit-3.3.1.war"

    info "Setting up Gerrit"

    cd "${buildDir}"
    mkdir gerrit
    cd gerrit
    wget "${gerritUrl}" -O gerrit.war

    java -jar gerrit.war init -b
    git config --file etc/gerrit.config auth.type DEVELOPMENT_BECOME_ANY_ACCOUNT
    git config --file etc/gerrit.config httpd.listenUrl 'http://*:8081/'
}

startGerrit() {
    local gerritLog="${buildDir}/gerrit.log"

    info "Starting Gerrit, writing log to ${gerritLog}"

    cd "${buildDir}/gerrit"
    java -jar gerrit.war daemon &>> "${gerritLog}"
}

setupJenkins() {
    local jenkinsUrl="http://mirror.xmission.com/jenkins/war/2.275/jenkins.war"

    info "Setting up Jenkins"

    cd "${buildDir}"
    mkdir jenkins
    cd jenkins
    wget "${jenkinsUrl}" -O jenkins.war
}

startJenkins() {
    local jenkinsLog="${buildDir}/jenkins.log"

    info "Starting Jenkins, writing log to ${jenkinsLog}"

    cd "${buildDir}/jenkins"
    export JENKINS_HOME="${PWD}"
    java -jar jenkins.war -Djenkins.install.runSetupWizard=false &>> "${jenkinsLog}"
}

setup() {
    mkdir "${buildDir}"
    setupGerrit
    setupJenkins
}

start() {
    startGerrit &
    startJenkins &

    wait
}

cleanup() {
    info "Cleaning up"

    [ -d "${buildDir}" ] && rm -rf "${buildDir}"
}

if [ "${1:-}" == "clean" ]; then
    cleanup
elif [ -e "${buildDir}" ]; then
    start
else
    setup
fi
