## 初始化
这里参考Retrofit使用ConverterFactory动态生成converter，可以添加多个ConverterFactory,内部会逐个尝试，若最后
解析失败会抛出异常，内部已经定义了StringConverterFactory、BitmapConverterFactory以及GsonConverterFactory,
支持自定义ConverterFactory.
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

## TO DO
- [] 文件上传下载以及进度监听
- [] cookie session处理
- [] 仿Retrofit使用接口和注解构建请求
- [] ........