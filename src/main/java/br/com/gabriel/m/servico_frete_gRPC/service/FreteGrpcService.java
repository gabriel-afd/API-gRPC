package br.com.gabriel.m.servico_frete_gRPC.service;

import com.gabriel.frete.proto.FreteRequest;
import com.gabriel.frete.proto.FreteResponse;
import com.gabriel.frete.proto.FreteServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class FreteGrpcService extends FreteServiceGrpc.FreteServiceImplBase {

    @Override
    public void calcular(FreteRequest request, StreamObserver<FreteResponse> responseObserver){
        double valor = 15.0 + request.getPesoKg() * 2.5;
        boolean mesmaRegiao = request.getCepOrigem().charAt(0) == request.getCepDestino().charAt(0);

        FreteResponse response = FreteResponse.newBuilder()
                .setValor(valor)
                .setPrazoDias(mesmaRegiao ? 2:5)
                .build();

        responseObserver.onNext(response); // envia a resposta
        responseObserver.onCompleted(); // encerra a chamada (sem isso o cliente fica esperando)
    }
}
