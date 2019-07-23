package com.jtools.common.config;

import java.util.List;

import com.google.common.base.Preconditions;

/**
 * The is the config file, will be auto load by the yaml with "config.yaml"
 *
 * @author qichengjie
 */
public class Config {

    private static Config _instance;

    public static void init(Config config) {
        _instance = config;
    }

    public static Config getInstance() {
        Preconditions.checkNotNull(_instance);
        return _instance;
    }

    // server port
    private int port;
    // log4j config path
    private String logConfigPath;
    // mt userid -> userConfig
    private String userConfigPath;
    private String userConfigFile;
    // city id -> city code
    private String citycodeFilePath;
    // pid -> city code [shanghai beijing chongqing tianjin]
    private String districtCitycodeFilePath;
    // pid -> city code [nanchang, changsha, wuhan ...]
    private String capitalCitycodeFilePath;
    // api,fee,key path
    private String apiPath;
    private String feePath;
    private String keystorePath;
    private String truststorePath;
    private String reloadWatchFilePath;
    private int reloadTimeMs;
    private int requestTimeOut;
    // ping an dict: cityCode -> pingAnDictBean
    private String pingAnDictPath;
    // BigPipe config
    private boolean bigpipeSwitchOn;
    private String bigpipeClusterName;
    private String bigpipePipeName;
    private String bigpipeUserName;
    private String bigpipePassword;
    private String bigpipeConnectionString;
    private int bigpipeSessionTimeoutms;
    private int bigpipeRetryTimes;
    private int bigpipeRetryWaitTimems;
    private int bigpipePipeletNum;
    private int mtTelephoneMaxSendCount;
    private int mtIpMaxSendCount;
    private int maxCacheExpireTime;
    private int maxCacheSize;
    private int mtSearchIdMaxSendCount;
    private boolean mtTelephoneFilterSwitchOn;
    private int userTaskRetrySleepSeconds;
    private int userTaskMaxRetry;
    private int userTaskRetryPowBase;
    private boolean userTaskRetrySwitchOn;
    private int dumpQueueInfoIntervalSleepCounts;
    private boolean apiUserSafeLeadsSwitch;
    private List<String> redisServerUrls;
    private int redisMaxTotal;
    private int redisMaxWaitMillis;
    private int redisMaxIdle;
    private List<String> prodbRedisServerUrls1;
    private List<String> prodbRedisServerUrls2;
    private List<String> elasticSearchAddress;
    private String queryAnalyzerRootPath;

    public int getDumpQueueInfoIntervalSleepCounts() {
        return dumpQueueInfoIntervalSleepCounts;
    }

    public void setDumpQueueInfoIntervalSleepCounts(int dumpQueueInfoIntervalSleepCounts) {
        this.dumpQueueInfoIntervalSleepCounts = dumpQueueInfoIntervalSleepCounts;
    }

    public boolean isUserTaskRetrySwitchOn() {
        return userTaskRetrySwitchOn;
    }

    public void setUserTaskRetrySwitchOn(boolean userTaskRetrySwitchOn) {
        this.userTaskRetrySwitchOn = userTaskRetrySwitchOn;
    }

    public int getUserTaskRetryPowBase() {
        return userTaskRetryPowBase;
    }

    public void setUserTaskRetryPowBase(int userTaskRetryPowBase) {
        this.userTaskRetryPowBase = userTaskRetryPowBase;
    }

    public int getUserTaskMaxRetry() {
        return userTaskMaxRetry;
    }

    public void setUserTaskMaxRetry(int userTaskMaxRetry) {
        this.userTaskMaxRetry = userTaskMaxRetry;
    }

    public int getUserTaskRetrySleepSeconds() {
        return userTaskRetrySleepSeconds;
    }

    public void setUserTaskRetrySleepSeconds(int userTaskRetrySleepSeconds) {
        this.userTaskRetrySleepSeconds = userTaskRetrySleepSeconds;
    }

    public String getUserConfigFile() {
        return userConfigFile;
    }

    public void setUserConfigFile(String userConfigFile) {
        this.userConfigFile = userConfigFile;
    }

    public int getMaxCacheExpireTime() {
        return maxCacheExpireTime;
    }

    public void setMaxCacheExpireTime(int maxCacheExpireTime) {
        this.maxCacheExpireTime = maxCacheExpireTime;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public String getReloadWatchFilePath() {
        return reloadWatchFilePath;
    }

    public void setReloadWatchFilePath(String reloadWatchFilePath) {
        this.reloadWatchFilePath = reloadWatchFilePath;
    }

    public int getMtSearchIdMaxSendCount() {
        return mtSearchIdMaxSendCount;
    }

    public void setMtSearchIdMaxSendCount(int mtSearchIdMaxSendCount) {
        this.mtSearchIdMaxSendCount = mtSearchIdMaxSendCount;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getFeePath() {
        return feePath;
    }

    public void setFeePath(String feePath) {
        this.feePath = feePath;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getTruststorePath() {
        return truststorePath;
    }

    public void setTruststorePath(String truststorePath) {
        this.truststorePath = truststorePath;
    }

    public int getReloadTimeMs() {
        return reloadTimeMs;
    }

    public void setReloadTimeMs(int reloadTimeMs) {
        this.reloadTimeMs = reloadTimeMs;
    }

    public String getUserConfigPath() {
        return userConfigPath;
    }

    public void setUserConfigPath(String userConfigPath) {
        this.userConfigPath = userConfigPath;
    }

    public int getMtIpMaxSendCount() {
        return mtIpMaxSendCount;
    }

    public void setMtIpMaxSendCount(int mtIpMaxSendCount) {
        this.mtIpMaxSendCount = mtIpMaxSendCount;
    }

    public void setMtTelephoneMaxSendCount(int sendCount) {
        this.mtTelephoneMaxSendCount = sendCount;
    }

    public int getMtTelephoneMaxSendCount() {
        return mtTelephoneMaxSendCount;
    }

    public boolean isMtTelephoneFilterSwitchOn() { return mtTelephoneFilterSwitchOn; }

    public void setMtTelephoneFilterSwitchOn(boolean switchOn) {
        this.mtTelephoneFilterSwitchOn = switchOn;
    }

    public boolean getMtTelephoneFilterSwitchOn() {
        return this.mtTelephoneFilterSwitchOn;
    }

    public boolean isBigpipeSwitchOn() {
        return bigpipeSwitchOn;
    }

    public void setBigpipeSwitchOn(boolean bigpipeSwitchOn) {
        this.bigpipeSwitchOn = bigpipeSwitchOn;
    }

    public int getBigpipePipeletNum() {
        return bigpipePipeletNum;
    }

    public void setBigpipePipeletNum(int bigpipePipeletNum) {
        this.bigpipePipeletNum = bigpipePipeletNum;
    }

    public int getBigpipeRetryTimes() {
        return bigpipeRetryTimes;
    }

    public void setBigpipeRetryTimes(int bigpipeRetryTimes) {
        this.bigpipeRetryTimes = bigpipeRetryTimes;
    }

    public int getBigpipeRetryWaitTimems() {
        return bigpipeRetryWaitTimems;
    }

    public void setBigpipeRetryWaitTimems(int bigpipeRetryWaitTimems) {
        this.bigpipeRetryWaitTimems = bigpipeRetryWaitTimems;
    }

    public String getBigpipeClusterName() {
        return bigpipeClusterName;
    }

    public void setBigpipeClusterName(String bigpipeClusterName) {
        this.bigpipeClusterName = bigpipeClusterName;
    }

    public String getBigpipePipeName() {
        return bigpipePipeName;
    }

    public void setBigpipePipeName(String bigpipePipeName) {
        this.bigpipePipeName = bigpipePipeName;
    }

    public String getBigpipeUserName() {
        return bigpipeUserName;
    }

    public void setBigpipeUserName(String bigpipeUserName) {
        this.bigpipeUserName = bigpipeUserName;
    }

    public String getBigpipePassword() {
        return bigpipePassword;
    }

    public void setBigpipePassword(String bigpipePassword) {
        this.bigpipePassword = bigpipePassword;
    }

    public String getBigpipeConnectionString() {
        return bigpipeConnectionString;
    }

    public void setBigpipeConnectionString(String bigpipeConnectionString) {
        this.bigpipeConnectionString = bigpipeConnectionString;
    }

    public int getBigpipeSessionTimeoutms() {
        return bigpipeSessionTimeoutms;
    }

    public void setBigpipeSessionTimeoutms(int bigpipeSessionTimeoutms) {
        this.bigpipeSessionTimeoutms = bigpipeSessionTimeoutms;
    }

    public int getRequestTimeOut() {
        return requestTimeOut;
    }

    public void setRequestTimeOut(int requestTimeOut) {
        this.requestTimeOut = requestTimeOut;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLogConfigPath() {
        return logConfigPath;
    }

    public void setLogConfigPath(String logConfigPath) {
        this.logConfigPath = logConfigPath;
    }

    public String getCitycodeFilePath() {
        return citycodeFilePath;
    }

    public void setCitycodeFilePath(String citycodeFilePath) {
        this.citycodeFilePath = citycodeFilePath;
    }

    public String getDistrictCitycodeFilePath() {
        return districtCitycodeFilePath;
    }

    public void setDistrictCitycodeFilePath(String districtCitycodeFilePath) {
        this.districtCitycodeFilePath = districtCitycodeFilePath;
    }

    public String getCapitalCitycodeFilePath() {
        return capitalCitycodeFilePath;
    }

    public void setCapitalCitycodeFilePath(String capitalCitycodeFilePath) {
        this.capitalCitycodeFilePath = capitalCitycodeFilePath;
    }

    public String getPingAnDictPath() {
        return pingAnDictPath;
    }

    public void setPingAnDictPath(String pingAnDictPath) {
        this.pingAnDictPath = pingAnDictPath;
    }

    public boolean isApiUserSafeLeadsSwitch() {
        return apiUserSafeLeadsSwitch;
    }

    public void setApiUserSafeLeadsSwitch(boolean apiUserSafeLeadsSwitch) {
        this.apiUserSafeLeadsSwitch = apiUserSafeLeadsSwitch;
    }

    public List<String> getRedisServerUrls() {
        return redisServerUrls;
    }

    public void setRedisServerUrls(List<String> redisServerUrls) {
        this.redisServerUrls = redisServerUrls;
    }

    public int getRedisMaxTotal() {
        return redisMaxTotal;
    }

    public void setRedisMaxTotal(int redisMaxTotal) {
        this.redisMaxTotal = redisMaxTotal;
    }

    public int getRedisMaxWaitMillis() {
        return redisMaxWaitMillis;
    }

    public void setRedisMaxWaitMillis(int redisMaxWaitMillis) {
        this.redisMaxWaitMillis = redisMaxWaitMillis;
    }

    public int getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(int redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
    }

    public List<String> getProdbRedisServerUrls1() {
        return prodbRedisServerUrls1;
    }

    public void setProdbRedisServerUrls1(List<String> prodbRedisServerUrls1) {
        this.prodbRedisServerUrls1 = prodbRedisServerUrls1;
    }

    public List<String> getProdbRedisServerUrls2() {
        return prodbRedisServerUrls2;
    }

    public void setProdbRedisServerUrls2(List<String> prodbRedisServerUrls2) {
        this.prodbRedisServerUrls2 = prodbRedisServerUrls2;
    }

    public List<String> getElasticSearchAddress() {
        return elasticSearchAddress;
    }

    public void setElasticSearchAddress(List<String> elasticSearchAddress) {
        this.elasticSearchAddress = elasticSearchAddress;
    }

    public String getQueryAnalyzerRootPath() {
        return queryAnalyzerRootPath;
    }

    public void setQueryAnalyzerRootPath(String queryAnalyzerRootPath) {
        this.queryAnalyzerRootPath = queryAnalyzerRootPath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{Config,port=");
        builder.append(port);
        builder.append(",logConfigPath=");
        builder.append(logConfigPath);
        builder.append(",userConfigPath=");
        builder.append(userConfigPath);
        builder.append(",userConfigFile=");
        builder.append(userConfigFile);
        builder.append(",citycodeFilePath=");
        builder.append(citycodeFilePath);
        builder.append(",districtCitycodeFilePath=");
        builder.append(districtCitycodeFilePath);
        builder.append(",capitalCitycodeFilePath=");
        builder.append(capitalCitycodeFilePath);
        builder.append(",apiPath=");
        builder.append(apiPath);
        builder.append(",feePath=");
        builder.append(feePath);
        builder.append(",keystorePath=");
        builder.append(keystorePath);
        builder.append(",truststorePath=");
        builder.append(truststorePath);
        builder.append(",reloadWatchFilePath=");
        builder.append(reloadWatchFilePath);
        builder.append(",reloadTimeMs=");
        builder.append(reloadTimeMs);
        builder.append(",requestTimeOut=");
        builder.append(requestTimeOut);
        builder.append(",pingAnDictPath=");
        builder.append(pingAnDictPath);
        builder.append(",bigpipeSwitchOn=");
        builder.append(bigpipeSwitchOn);
        builder.append(",bigpipeClusterName=");
        builder.append(bigpipeClusterName);
        builder.append(",bigpipePipeName=");
        builder.append(bigpipePipeName);
        builder.append(",bigpipeUserName=");
        builder.append(bigpipeUserName);
        builder.append(",bigpipePassword=");
        builder.append(bigpipePassword);
        builder.append(",bigpipeConnectionString=");
        builder.append(bigpipeConnectionString);
        builder.append(",bigpipeSessionTimeoutms=");
        builder.append(bigpipeSessionTimeoutms);
        builder.append(",bigpipeRetryTimes=");
        builder.append(bigpipeRetryTimes);
        builder.append(",bigpipeRetryWaitTimems=");
        builder.append(bigpipeRetryWaitTimems);
        builder.append(",bigpipePipeletNum=");
        builder.append(bigpipePipeletNum);
        builder.append(",mtTelephoneMaxSendCount=");
        builder.append(mtTelephoneMaxSendCount);
        builder.append(",mtIpMaxSendCount=");
        builder.append(mtIpMaxSendCount);
        builder.append(",maxCacheExpireTime=");
        builder.append(maxCacheExpireTime);
        builder.append(",maxCacheSize=");
        builder.append(maxCacheSize);
        builder.append(",mtSearchIdMaxSendCount=");
        builder.append(mtSearchIdMaxSendCount);
        builder.append(",mtTelephoneFilterSwitchOn=");
        builder.append(mtTelephoneFilterSwitchOn);
        builder.append(",userTaskRetrySleepSeconds=");
        builder.append(userTaskRetrySleepSeconds);
        builder.append(",userTaskMaxRetry=");
        builder.append(userTaskMaxRetry);
        builder.append(",userTaskRetryPowBase=");
        builder.append(userTaskRetryPowBase);
        builder.append(",userTaskRetrySwitchOn=");
        builder.append(userTaskRetrySwitchOn);
        builder.append(",dumpQueueInfoIntervalSleepCounts=");
        builder.append(dumpQueueInfoIntervalSleepCounts);
        builder.append(",apiUserSafeLeadsSwitch=");
        builder.append(apiUserSafeLeadsSwitch);
        builder.append(",redisServerUrls=");
        builder.append(redisServerUrls);
        builder.append(",redisMaxTotal=");
        builder.append(redisMaxTotal);
        builder.append(",redisMaxWaitMillis=");
        builder.append(redisMaxWaitMillis);
        builder.append(",redisMaxIdle=");
        builder.append(redisMaxIdle);
        builder.append(",elasticSearchAddress=");
        builder.append(elasticSearchAddress);
        builder.append(",queryAnalyzerRootPath=");
        builder.append(queryAnalyzerRootPath);
        builder.append("}");
        return builder.toString();
    }
}
