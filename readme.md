## web页面H5：  
new WebSocket("ws://127.0.0.1:9091/cloud-sbjm/myHandler/ID="+userID)，与服务器建立webSocket连接，后面的ID="+userID，是动态参数，跟服务器配置Handler的访问地址时对应"/myHandler/{ID}"。

H5也提供多个回调函数

onopen：打开webSokcet连接时，回调该函数

onclose：关闭webSocket连接时，回调该函数

onmessage：服务器给该socket发送消息时，回调该函数，获取消息

websocket.send(JSON.stringify(postValue));：给Socket发送消息，服务器获取

websocket.close();客户端主要关闭连接，会触发客户端的onclose方法和服务器的afterConnectionClosed方法

## 后端代码说明：   
实现了WebSocketHandler接口，并实现了关键的几个方法。

① afterConnectionEstablished（接口提供的）：建立新的socket连接后回调的方法。主要逻辑是：将成功建立连接的webSocketSssion放到定义好的常量[private static final Map<String, WebSocketSession> users;]中去。这里也截取客户端访问的URL的字符串，拿到标识，以键值对的形式讲每一个webSocketSession存到users里，以记录每个Socket。

② handleMessage（接口提供的）：接收客户端发送的Socket。主要逻辑是：获取客户端发送的信息。这里之所以可以获取本次Socket的ID，是因为客户端在第一次进行连接时，拦截器进行拦截后，设置好ID，这样也说明，双方在相互通讯的时候，只是对第一次建立好的socket持续进行操作。

③ sendMessageToUser（自己定义的）：发送给指定用户信息。主要逻辑是：根据用户ID从常量users(记录每一个Socket)中，获取Socket,往该Socket里发送消息，只要客户端还在线，就能收到该消息。

④sendMessageToAllUsers （自己定义的）：这个广播消息，发送信息给所有socket。主要逻辑是：跟③类型，只不过是遍历整个users获取每一个socket,给每一个socket发送消息即可完广播发送

⑤handleTransportError（接口提供的）：连接出错时，回调的方法。主要逻辑是：一旦有连接出错的Socket,就从users里进行移除，有提供该Socket的参数，可直接获取ID，进行移除。这个在客户端没有正常关闭连接时，会进来，所以在开发客户端时，记得关闭连接

⑥afterConnectionClosed（接口提供的）：连接关闭时，回调的方法。主要逻辑：一旦客户端/服务器主动关闭连接时，将个socket从users里移除，有提供该Socket的参数，可直接获取ID，进行移除。
