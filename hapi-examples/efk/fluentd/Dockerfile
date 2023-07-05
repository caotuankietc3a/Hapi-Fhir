# FROM fluent/fluentd:v1.16.0-debian-1.0
FROM fluent/fluentd:v1.14.0-debian-1.0
USER root
RUN buildDeps="sudo make gcc g++ libc-dev ruby-dev" \
	&& apt-get update \
	&& apt-get install -y --no-install-recommends $buildDeps 

RUN apt-get install -y build-essential libgeoip-dev libmaxminddb-dev

RUN gem sources

RUN fluent-gem install elasticsearch -v 7.10
RUN fluent-gem install fluent-plugin-elasticsearch --no-document --version 5.0.5
RUN fluent-gem install fluent-plugin-geoip --no-document

USER fluent
