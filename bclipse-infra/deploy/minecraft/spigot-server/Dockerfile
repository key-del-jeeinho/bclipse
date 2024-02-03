FROM openjdk:17

WORKDIR /spigot-server

ADD https://download.getbukkit.org/spigot/spigot-1.20.4.jar spigot.jar
RUN echo "eula=true" > eula.txt

EXPOSE 25565

RUN echo "#!/bin/bash" > start.sh && \
    echo "java -Xms1G -Xmx8G -jar spigot.jar" >> start.sh && \
    chmod +x start.sh
