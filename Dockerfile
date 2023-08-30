FROM node:lts-alpine3.18
WORKDIR /opt/app
COPY gate-simulator /opt/app
RUN npm install
CMD ["npm", "start"]