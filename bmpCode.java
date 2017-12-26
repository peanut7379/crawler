public void startProxy() {      
  proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        /*proxy.setMitmManager(ImpersonatingMitmManager.builder().trustAllServers(true).build());*/
        proxy.start(0);
        proxyPort = proxy.getPort();

        logger.info("Proxy started @port {}", proxyPort);

        RequestFilter filter = new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {

                logger.info("[DEBUG] originalUrl:" + messageInfo.getOriginalUrl());
                if (messageInfo.getOriginalUrl().contains("https://live.direct.ly//rest/lives/v1/discovery/channels/latest?viewedId=")) {

                    logger.info(messageInfo.getOriginalUrl());

                    logger.info("[DEBUG]X-Request-ID:" + messageInfo.getOriginalRequest().headers().get("X-Request-ID"));
                    logger.info("[DEBUG]X-Request-Info5:" + messageInfo.getOriginalRequest().headers().get("X-Request-Info5"));
                    logger.info("[DEBUG]X-Request-Sign5:" + messageInfo.getOriginalRequest().headers().get("X-Request-Sign5"));
                    logger.info("[DEBUG]Authorization:" + messageInfo.getOriginalRequest().headers().get("Authorization"));
                    logger.info("[DEBUG]build:" + messageInfo.getOriginalRequest().headers().get("build"));

                    //AccessConfidential ac = new AccessConfidential(
                    //        messageInfo.getOriginalRequest().headers().get("X-Request-ID"),
                    //        messageInfo.getOriginalRequest().headers().get("X-Request-Info5"),
                    //        messageInfo.getOriginalRequest().headers().get("X-Request-Sign5"),
                    //        messageInfo.getOriginalRequest().headers().get("Authorization"),
                    //        messageInfo.getOriginalRequest().headers().get("build"),
                    //        0L
                    //);

                    try {
                        //TODO:
                        // ac.insert();
                        logger.info("TODO:");
                    } catch (Exception e) {
                        logger.error("Error insert AccessConfidential, ", e);
                    }
                }

                return null;
            }
        };

        proxy.addFirstHttpFilterFactory(new RequestFilterAdapter.FilterSource(filter, 16777216));

        proxy.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {

                if (messageInfo.getOriginalUrl().contains("https://live.direct.ly//rest/lives/v1/discovery/channels/latest?viewedId=")) {

                    String uid = contents.getTextContents().replaceAll("^.+?\"id\":", "").replaceAll(",.+?$", "");

                    try {

                        if (!Crawler.userIds.contains(uid) && !Crawler.userIdQueueSet.contains(uid)) {
                            Crawler.userIdQueue.put(uid);
                            Crawler.userIdQueueSet.add(uid);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }