FROM kchale/chisel:latest

ARG USER_ID
ARG GROUP_ID

RUN addgroup -gid $GROUP_ID hussain
RUN adduser --disabled-password --gecos '' --uid $USER_ID --gid $GROUP_ID hussain
USER hussain
