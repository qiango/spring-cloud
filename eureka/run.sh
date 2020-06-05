#!/bin/bash
#server
#description: Auto-starts boot

Tag="eureka"
Active="active"
echo $Tag $Active
RETVAL="0"

# See how we were called.
function start(){
    pid=$(ps -ef | grep -v 'grep' | grep $Tag | grep $Active | awk '{printf $2 " "}')
    if [ "$pid" == "" ]; then
        nohup java -jar target/eureka-0.0.1-SNAPSHOT.jar --spring.profiles.$Active  > catalina.out  2>&1 &
        echo 12345678

fi
    status
}


function stop() {
    pid=$(ps -ef | grep -v 'grep' | grep $Tag | grep $Active | awk '{printf $2 " "}')
    if [ "$pid" != "" ]; then
        echo -n "boot ( pid $pid) is running"
        echo
        echo -n $"Shutting down boot: "
        pid=$(ps -ef | grep -v 'grep' | grep $Tag | grep $Active | awk '{printf $2 " "}')
        if [ "$pid" != "" ]; then
            echo "kill boot process"
            kill -9 "$pid"
        fi
        fi

    status
}

function status()
{
    pid=$(ps -ef | grep -v 'grep' | grep $Tag | grep $Active | awk '{printf $2 " "}')
    #echo "$pid"
    if [ "$pid" != "" ]; then
        echo "boot is running,pid is $pid"
    else
        echo "boot is stopped"
    fi
}


"run.sh" 81L, 1518C
