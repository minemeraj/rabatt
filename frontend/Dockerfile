FROM node:boron

# Create app directory
RUN mkdir -p /code
WORKDIR /code

# Install app dependencies
COPY package.json /code/
COPY bower.json /code/
COPY .bowerrc /code/
RUN npm install
RUN npm install -g bower
RUN bower install --allow-root

# Bundle app source
COPY . /code

EXPOSE 3000
CMD [ "node", "index.js" ]
