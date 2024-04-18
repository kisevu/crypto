#   CRYPTO RANKING FULL STACK APP

### How to use the created application, which I will host soon 😍😍😎🥰😗

Prerequisites to get the app running:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.4/maven-plugin/reference/html/)
* [docker plugin](https://docs.spring.io/spring-boot/docs/3.2.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.4/reference/htmlsingle/index.html#web)
* [Redis](https://redis.io/docs/latest/)
* [coin ranking](https://rapidapi.com/Coinranking/api/coinranking1)
* [docker](https://hub.docker.com/)


### Breakdown

* Basically, I am make an external API call to the coin ranking app hosted on rapid api https://rapidapi.com/
and I am storing that data on redis and also making use of redis time series and other beautiful features that 
redis has to offer right out of the box.
* I have correctly formatting the timeStamp and making it available for my spring boot rest API which later I am
displaying the data on my front end. Pls refer to the codes if you're following.
* Ooh okay, so I have my ui ready for use and basically what am doing is display data and correctly doing some 
filtering on a single entry request.

### steps to run

* Ensure your docker daemon is running and you have the redis image on it and configure env properly.
* Ensure that you have some open endpoints which successfully makes it accessible to your backend.
* Ensure that your ui is exposing its endpoint which is correctly configured in backend application and vice versa
* Hooray! 😁 test you application 😁 🐾

## Below are the images support the documentation:
![Time stamp visualization](https://github.com/kisevu/crypto/blob/main/src/main/resources/images/visualize%20by%20timestamp.PNG)
* redis Time series in action.

![filter by time on my rest API](https://github.com/kisevu/crypto/blob/main/src/main/resources/images/filter%20by%20time.PNG)
* proxied and successfully retrieve through custom endpoint
![Querying through redis](https://github.com/kisevu/crypto/blob/main/src/main/resources/images/redis%20query%20CLI.PNG)
* Querying data stored on redis
![Single entry query via custom API](https://github.com/kisevu/crypto/blob/main/src/main/resources/images/single%20crypto.PNG)
* Single entry query
