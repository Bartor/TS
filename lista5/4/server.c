#include <stdio.h>
#include <string.h>
#include <winsock2.h>
#include <windows.h>

DWORD WINAPI ThreadFunc(void* data) {
    SOCKET socket = *((SOCKET*) data);
    char reply[1 << 16];
    int recv_size = 0;
    if ((recv_size = recv(socket, reply, 1 << 16, 0)) == SOCKET_ERROR) {
        printf("receive error");
    } else {
        reply[recv_size] = '\0';

        printf("%s\n", reply);

        char curr;
        int i = -1;
        while ((curr = reply[++i]) != '\n');
        printf("%d\n", send(socket, (reply+i), strlen(reply) - i, 0));
    }

    closesocket(socket);
    return 0;
}

//gcc compile with "-lwsock32" added
int main(int argc , char** argv) {
	WSADATA wsa;
	SOCKET s;
	struct sockaddr_in server, client;

	if (WSAStartup(MAKEWORD(2,2),&wsa) != 0) {
		printf("Failed. Error Code : %d",WSAGetLastError());
		return 1;
	}
	
	printf("Initialised.\n");
	
	if((s = socket(AF_INET , SOCK_STREAM , 0 )) == INVALID_SOCKET) {
		printf("Could not create socket : %d" , WSAGetLastError());
	}

	printf("Socket created.\n");
	
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons( 8888 );
	
	if(bind(s ,(struct sockaddr *)&server , sizeof(server)) == SOCKET_ERROR) {
		printf("Bind failed with error code : %d" , WSAGetLastError());
	}
	
	puts("Bind done");
	
	listen(s , 3);
	
	puts("Waiting for incoming connections...");
	
	int c = sizeof(struct sockaddr_in);

    while (1) {
	    SOCKET new_socket = accept(s , (struct sockaddr*) &client, &c);
        if (new_socket == INVALID_SOCKET) {
            printf("accept failed with error code : %d" , WSAGetLastError());
        } else {
            //pass new socket to the thread
            HANDLE thread = CreateThread(NULL, 0, ThreadFunc, &new_socket, 0, NULL);
        }
    }

	puts("Connection accepted");

	closesocket(s);
	WSACleanup();

	return 0;
}