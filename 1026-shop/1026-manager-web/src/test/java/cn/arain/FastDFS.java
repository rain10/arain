package cn.arain;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.arain.common.util.FastDFSClient;

public class FastDFS {
	
	@Test
	public void upload() throws Exception {
		//初始化
		ClientGlobal.init("E:/none/1026-manager-web/src/main/resources/conf/client.conf");	
		//tracker_client
		TrackerClient trackerClient = new TrackerClient();
		//tracker_server
		TrackerServer trackerServer = trackerClient.getConnection();
		//storageServer
		StorageServer storageServer = null;
		//storageClient
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//上传
		String[] file = storageClient.upload_file("F:/picture/f3d3343543a240c08d8eb83ef464e422.jpg", "jpg", null);
		for (String string : file) {
			System.out.println(string);
		}
		
	}
	
	@Test
	public void utilupload() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("E:/none/1026-manager-web/src/main/resources/conf/client.conf");
		String uploadFile = fastDFSClient.uploadFile("F:/picture/8f47dafdfe8f43709ce5e2b9ce175c25.jpg", "jpg", null);
		System.out.println(uploadFile);
	}
}
