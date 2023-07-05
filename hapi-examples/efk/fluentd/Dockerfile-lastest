# fluentd/Dockerfile
FROM fluent/fluentd:edge-debian

# Use root account to use apk
USER root

RUN fluent-gem install fluent-plugin-elasticsearch

# below RUN includes plugin as examples elasticsearch is not required
# you may customize including plugins as you wish

USER fluent
