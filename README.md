## 简介
这是一个Android平台的异步网络请求工具，底层网络请求基于OkHttp.封装了请求的构建以及结果的解析
参考Retrofit使用ConverterFactory动态生成converter，可以添加多个ConverterFactory,内部会逐个尝试，
若最后解析失败会回调onError方法，内部已经定义了StringConverterFactory、BitmapConverterFactory以及GsonConverterFactory,
支持自定义ConverterFactory.

目前支持的功能如下：
* 一般的get、post请求
* post提交表单、表单方式上传文件、post提交文本
* bitmap的加载以及解析、一般文件的下载以及进度监听
* 文件上传进度监听，进度监听回调方法运行在主线程
* 响应内容的解析，默认解析为字符串，支持解析为实体对象
* 线程切换，在回调方法中拿到结果可以直接更新UI

## 初始化

```
EasyNet net = new EasyNet.Builder()
                //.setClient(new OkHttpClient())
                .addConverterFactory(BitmapConverterFactory.create()) //添加图片解析
                .addConverterFactory(GsonConverterFactory.create()) //添加实体类解析
                .build();
```
## 简单的GET请求
```
net.get()
    .url(url)
    .add
    //.addHeader()
    //.addParam() 添加参数
    //.params() 添加多个参数
    .build()
    .execute(new Callback<String>(){
        @Override
        public void onSuccess(Response<String> response) {
            Log.i(TAG, "onSuccess: "+response.data);
            //请求成功，主线程，可以直接更新UI
        }

        @Override
        public void onError(Call call, IOException e) {

        }
    });
```
## 请求一个Bean
```
net.get()
    .url(url)
    //.addParam()
    //.params()
    //.addHeader()
    .build()
    .execute(new Callback<Bean>(){
        @Override
        public void onSuccess(Response<Bean> response) {
            //直接获取bean
        }

        @Override
        public void onError(Call call, IOException e) {

        }
    });
```
## 简单加载一个图像
没有做任何关于缓存之类的处理，只适合简单的图片加载
```
net.get()
    .url(url)
    .build()
    .execute(new Callback<Bitmap>(){
        @Override
        public void onSuccess(Response<Bitmap> response) {
            ivResult.setImageBitmap(response.data);
        }

        @Override
        public void onError(Call call, IOException e) {

        }
    });
```
## POST表单
```
net.post().url(url)
    .addParam("user","masker")
    .addParam("password","123456")
    .build()
    .execute(new Callback<String>(){
        @Override
        public void onSuccess(Response<String> response) {

        }

        @Override
        public void onError(Call call, IOException e) {

        }
    });
```
## POST文本
```
String content = "content";
net.post(content).url(url)
        .build()
        .execute(new Callback<String>() {
            @Override
            public void onSuccess(Response<String> response) {

            }

            @Override
            public void onError(Call call, IOException e) {

            }
        });
```
## 文件下载
```
net.get().url(url)
        .build()
        .execute(new FileCallback(path) { //path为文件的路径
            @Override
            public void onUpdateProgress(long curLength, long totalLength) {
                int progress = (int)(curLength*100L/totalLength);
                progressBar.setProgress(progress);
            }
            
            @Override
            public void onSuccess(Response<File> response) {
                
            }

            @Override
            public void onError(Call call, IOException e) {

            }
        });
```
## post表单上传文件
```
net.upload()
    .url(uploadUrl)
    .file(file,"music")//前面是要上传的文件，后面的表单中文件对应的name
    .file(upFile,"apk")
    .listener(new EasyRequestBody.UpLoadListener() {
        @Override
        public void onUpdateProgress(long curLength, long totalLength) {
            int progress = (int)(curLength*100L/totalLength);
            progressBar.setProgress(progress);
            tvProgress.setText(" "+(curLength/1024)+"/"+(totalLength/1024)+"Kb");
        }
    })
    .build()
    .execute(new Callback<String>(){
        @Override
        public void onSuccess(Response<String> response) {
            Log.i(TAG, "onSuccess: "+response.data);
        }

        @Override
        public void onError(Call call, IOException e) {

        }
    });
```

## TO DO
- [x] 文件上传下载以及进度监听
- [ ] cookie session处理
- [ ] 仿Retrofit使用接口和注解构建请求
- [ ] ........
