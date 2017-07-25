package grpc.videoservice;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.1.2)",
    comments = "Source: videoservice.proto")
public class VideoServiceGrpc {

  private VideoServiceGrpc() {}

  private static <T> io.grpc.stub.StreamObserver<T> toObserver(final io.vertx.core.Handler<io.vertx.core.AsyncResult<T>> handler) {
    return new io.grpc.stub.StreamObserver<T>() {
      private volatile boolean resolved = false;
      @Override
      public void onNext(T value) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture(value));
        }
      }

      @Override
      public void onError(Throwable t) {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.failedFuture(t));
        }
      }

      @Override
      public void onCompleted() {
        if (!resolved) {
          resolved = true;
          handler.handle(io.vertx.core.Future.succeededFuture());
        }
      }
    };
  }

  public static final String SERVICE_NAME = "VideoService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.videoservice.VideoRequest,
      grpc.videoservice.Video> METHOD_GET_VIDEO =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "VideoService", "getVideo"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.VideoRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.Video.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.videoservice.VideoIdsRequest,
      grpc.videoservice.Videos> METHOD_MGET_VIDEOS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "VideoService", "mgetVideos"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.VideoIdsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.Videos.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.videoservice.VideoIdsRequest,
      grpc.videoservice.Video> METHOD_MGET_VIDEOS_BY_STREAM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "VideoService", "mgetVideosByStream"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.VideoIdsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.Video.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.videoservice.VideoRequest,
      grpc.videoservice.Video> METHOD_GET_VIDEOS_BY_STREAM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "VideoService", "getVideosByStream"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.VideoRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.videoservice.Video.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VideoServiceStub newStub(io.grpc.Channel channel) {
    return new VideoServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VideoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new VideoServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static VideoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new VideoServiceFutureStub(channel);
  }

  /**
   * Creates a new vertx stub that supports all call types for the service
   */
  public static VideoServiceVertxStub newVertxStub(io.grpc.Channel channel) {
    return new VideoServiceVertxStub(channel);
  }

  /**
   */
  public static abstract class VideoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getVideo(grpc.videoservice.VideoRequest request,
        io.grpc.stub.StreamObserver<grpc.videoservice.Video> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_VIDEO, responseObserver);
    }

    /**
     */
    public void mgetVideos(grpc.videoservice.VideoIdsRequest request,
        io.grpc.stub.StreamObserver<grpc.videoservice.Videos> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MGET_VIDEOS, responseObserver);
    }

    /**
     */
    public void mgetVideosByStream(grpc.videoservice.VideoIdsRequest request,
        io.grpc.stub.StreamObserver<grpc.videoservice.Video> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MGET_VIDEOS_BY_STREAM, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.videoservice.VideoRequest> getVideosByStream(
        io.grpc.stub.StreamObserver<grpc.videoservice.Video> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_GET_VIDEOS_BY_STREAM, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_VIDEO,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.videoservice.VideoRequest,
                grpc.videoservice.Video>(
                  this, METHODID_GET_VIDEO)))
          .addMethod(
            METHOD_MGET_VIDEOS,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.videoservice.VideoIdsRequest,
                grpc.videoservice.Videos>(
                  this, METHODID_MGET_VIDEOS)))
          .addMethod(
            METHOD_MGET_VIDEOS_BY_STREAM,
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.videoservice.VideoIdsRequest,
                grpc.videoservice.Video>(
                  this, METHODID_MGET_VIDEOS_BY_STREAM)))
          .addMethod(
            METHOD_GET_VIDEOS_BY_STREAM,
            asyncBidiStreamingCall(
              new MethodHandlers<
                grpc.videoservice.VideoRequest,
                grpc.videoservice.Video>(
                  this, METHODID_GET_VIDEOS_BY_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class VideoServiceStub extends io.grpc.stub.AbstractStub<VideoServiceStub> {
    private VideoServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VideoServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VideoServiceStub(channel, callOptions);
    }

    /**
     */
    public void getVideo(grpc.videoservice.VideoRequest request,
        io.grpc.stub.StreamObserver<grpc.videoservice.Video> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_VIDEO, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mgetVideos(grpc.videoservice.VideoIdsRequest request,
        io.grpc.stub.StreamObserver<grpc.videoservice.Videos> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MGET_VIDEOS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mgetVideosByStream(grpc.videoservice.VideoIdsRequest request,
        io.grpc.stub.StreamObserver<grpc.videoservice.Video> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_MGET_VIDEOS_BY_STREAM, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.videoservice.VideoRequest> getVideosByStream(
        io.grpc.stub.StreamObserver<grpc.videoservice.Video> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_GET_VIDEOS_BY_STREAM, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class VideoServiceBlockingStub extends io.grpc.stub.AbstractStub<VideoServiceBlockingStub> {
    private VideoServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VideoServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VideoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.videoservice.Video getVideo(grpc.videoservice.VideoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_VIDEO, getCallOptions(), request);
    }

    /**
     */
    public grpc.videoservice.Videos mgetVideos(grpc.videoservice.VideoIdsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MGET_VIDEOS, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<grpc.videoservice.Video> mgetVideosByStream(
        grpc.videoservice.VideoIdsRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_MGET_VIDEOS_BY_STREAM, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class VideoServiceFutureStub extends io.grpc.stub.AbstractStub<VideoServiceFutureStub> {
    private VideoServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VideoServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VideoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.videoservice.Video> getVideo(
        grpc.videoservice.VideoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_VIDEO, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.videoservice.Videos> mgetVideos(
        grpc.videoservice.VideoIdsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MGET_VIDEOS, getCallOptions()), request);
    }
  }

  /**
   */
  public static abstract class VideoServiceVertxImplBase implements io.grpc.BindableService {

    /**
     */
    public void getVideo(grpc.videoservice.VideoRequest request,
        io.vertx.core.Future<grpc.videoservice.Video> response) {
      asyncUnimplementedUnaryCall(METHOD_GET_VIDEO, VideoServiceGrpc.toObserver(response.completer()));
    }

    /**
     */
    public void mgetVideos(grpc.videoservice.VideoIdsRequest request,
        io.vertx.core.Future<grpc.videoservice.Videos> response) {
      asyncUnimplementedUnaryCall(METHOD_MGET_VIDEOS, VideoServiceGrpc.toObserver(response.completer()));
    }

    /**
     */
    public void mgetVideosByStream(grpc.videoservice.VideoIdsRequest request,
        io.vertx.grpc.GrpcWriteStream<grpc.videoservice.Video> response) {
      asyncUnimplementedUnaryCall(METHOD_MGET_VIDEOS_BY_STREAM, response.writeObserver());
    }

    /**
     */
    public void getVideosByStream(
        io.vertx.grpc.GrpcBidiExchange<grpc.videoservice.VideoRequest, grpc.videoservice.Video> exchange) {
      exchange.setReadObserver(asyncUnimplementedStreamingCall(METHOD_GET_VIDEOS_BY_STREAM, exchange.writeObserver()));
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_VIDEO,
            asyncUnaryCall(
              new VertxMethodHandlers<
                grpc.videoservice.VideoRequest,
                grpc.videoservice.Video>(
                  this, METHODID_GET_VIDEO)))
          .addMethod(
            METHOD_MGET_VIDEOS,
            asyncUnaryCall(
              new VertxMethodHandlers<
                grpc.videoservice.VideoIdsRequest,
                grpc.videoservice.Videos>(
                  this, METHODID_MGET_VIDEOS)))
          .addMethod(
            METHOD_MGET_VIDEOS_BY_STREAM,
            asyncServerStreamingCall(
              new VertxMethodHandlers<
                grpc.videoservice.VideoIdsRequest,
                grpc.videoservice.Video>(
                  this, METHODID_MGET_VIDEOS_BY_STREAM)))
          .addMethod(
            METHOD_GET_VIDEOS_BY_STREAM,
            asyncBidiStreamingCall(
              new VertxMethodHandlers<
                grpc.videoservice.VideoRequest,
                grpc.videoservice.Video>(
                  this, METHODID_GET_VIDEOS_BY_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class VideoServiceVertxStub extends io.grpc.stub.AbstractStub<VideoServiceVertxStub> {
    private VideoServiceVertxStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VideoServiceVertxStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoServiceVertxStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VideoServiceVertxStub(channel, callOptions);
    }

    /**
     */
    public void getVideo(grpc.videoservice.VideoRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<grpc.videoservice.Video>> response) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_VIDEO, getCallOptions()), request, VideoServiceGrpc.toObserver(response));
    }

    /**
     */
    public void mgetVideos(grpc.videoservice.VideoIdsRequest request,
        io.vertx.core.Handler<io.vertx.core.AsyncResult<grpc.videoservice.Videos>> response) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MGET_VIDEOS, getCallOptions()), request, VideoServiceGrpc.toObserver(response));
    }

    /**
     */
    public void mgetVideosByStream(grpc.videoservice.VideoIdsRequest request,
        io.vertx.core.Handler<io.vertx.grpc.GrpcReadStream<grpc.videoservice.Video>> handler) {
      final io.vertx.grpc.GrpcReadStream<grpc.videoservice.Video> readStream =
          io.vertx.grpc.GrpcReadStream.<grpc.videoservice.Video>create();

      handler.handle(readStream);
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_MGET_VIDEOS_BY_STREAM, getCallOptions()), request, readStream.readObserver());
    }

    /**
     */
    public void getVideosByStream(io.vertx.core.Handler<
        io.vertx.grpc.GrpcBidiExchange<grpc.videoservice.Video, grpc.videoservice.VideoRequest>> handler) {
      final io.vertx.grpc.GrpcReadStream<grpc.videoservice.Video> readStream =
          io.vertx.grpc.GrpcReadStream.<grpc.videoservice.Video>create();

      handler.handle(io.vertx.grpc.GrpcBidiExchange.create(readStream, asyncBidiStreamingCall(
          getChannel().newCall(METHOD_GET_VIDEOS_BY_STREAM, getCallOptions()), readStream.readObserver())));
    }
  }

  private static final int METHODID_GET_VIDEO = 0;
  private static final int METHODID_MGET_VIDEOS = 1;
  private static final int METHODID_MGET_VIDEOS_BY_STREAM = 2;
  private static final int METHODID_GET_VIDEOS_BY_STREAM = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VideoServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(VideoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_VIDEO:
          serviceImpl.getVideo((grpc.videoservice.VideoRequest) request,
              (io.grpc.stub.StreamObserver<grpc.videoservice.Video>) responseObserver);
          break;
        case METHODID_MGET_VIDEOS:
          serviceImpl.mgetVideos((grpc.videoservice.VideoIdsRequest) request,
              (io.grpc.stub.StreamObserver<grpc.videoservice.Videos>) responseObserver);
          break;
        case METHODID_MGET_VIDEOS_BY_STREAM:
          serviceImpl.mgetVideosByStream((grpc.videoservice.VideoIdsRequest) request,
              (io.grpc.stub.StreamObserver<grpc.videoservice.Video>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_VIDEOS_BY_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getVideosByStream(
              (io.grpc.stub.StreamObserver<grpc.videoservice.Video>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static class VertxMethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VideoServiceVertxImplBase serviceImpl;
    private final int methodId;

    public VertxMethodHandlers(VideoServiceVertxImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_VIDEO:
          serviceImpl.getVideo((grpc.videoservice.VideoRequest) request,
              (io.vertx.core.Future<grpc.videoservice.Video>) io.vertx.core.Future.<grpc.videoservice.Video>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<grpc.videoservice.Video>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        case METHODID_MGET_VIDEOS:
          serviceImpl.mgetVideos((grpc.videoservice.VideoIdsRequest) request,
              (io.vertx.core.Future<grpc.videoservice.Videos>) io.vertx.core.Future.<grpc.videoservice.Videos>future().setHandler(ar -> {
                if (ar.succeeded()) {
                  ((io.grpc.stub.StreamObserver<grpc.videoservice.Videos>) responseObserver).onNext(ar.result());
                  responseObserver.onCompleted();
                } else {
                  responseObserver.onError(ar.cause());
                }
              }));
          break;
        case METHODID_MGET_VIDEOS_BY_STREAM:
          serviceImpl.mgetVideosByStream((grpc.videoservice.VideoIdsRequest) request,
              (io.vertx.grpc.GrpcWriteStream<grpc.videoservice.Video>) io.vertx.grpc.GrpcWriteStream.create(responseObserver));
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_VIDEOS_BY_STREAM:
          io.vertx.grpc.GrpcReadStream<grpc.videoservice.VideoRequest> request3 = io.vertx.grpc.GrpcReadStream.<grpc.videoservice.VideoRequest>create();
          serviceImpl.getVideosByStream(
             io.vertx.grpc.GrpcBidiExchange.<grpc.videoservice.VideoRequest, grpc.videoservice.Video>create(
               request3,
               (io.grpc.stub.StreamObserver<grpc.videoservice.Video>) responseObserver));
          return (io.grpc.stub.StreamObserver<Req>) request3.readObserver();
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class VideoServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.videoservice.VideoServiceProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (VideoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VideoServiceDescriptorSupplier())
              .addMethod(METHOD_GET_VIDEO)
              .addMethod(METHOD_MGET_VIDEOS)
              .addMethod(METHOD_MGET_VIDEOS_BY_STREAM)
              .addMethod(METHOD_GET_VIDEOS_BY_STREAM)
              .build();
        }
      }
    }
    return result;
  }
}
