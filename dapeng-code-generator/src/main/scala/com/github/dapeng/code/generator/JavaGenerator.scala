package com.github.dapeng.code.generator

import java.io._
import java.util

import com.github.dapeng.core.CustomConfig
import com.github.dapeng.core.metadata.DataType.KIND
import com.github.dapeng.core.metadata.TEnum.EnumItem
import com.github.dapeng.core.metadata._

import collection.JavaConverters._
import scala.xml.Elem

/**
  * JAVA生成器
  *
  * @author tangliu
  */
class JavaGenerator extends CodeGenerator {

  override def generate(services: util.List[Service], outDir: String): Unit = {}

  private def rootDir(rootDir: String, packageName: String): File = {
    val dir = rootDir + "/java/" + packageName.replaceAll("[.]", "/")

    val file = new File(dir)

    if(!file.exists())
      file.mkdirs()

    return file
  }

  private def resourceDir(rootDir: String, packageName: String): String = {
    val dir = rootDir + "/resources/"

    val file = new File(dir)

    if(!file.exists()){}
    file.mkdirs()

    dir
  }

  val notice: String = " * Autogenerated by Dapeng-Code-Generator (2.0.0)\n *\n * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING\n *  @generated\n"


  override def generate(services: util.List[Service], outDir: String, generateAll:Boolean , structs: util.List[Struct], enums:util.List[TEnum]): Unit = {

    val namespaces:util.Set[String] = new util.HashSet[String]();
    val structNamespaces:util.Set[String] = new util.HashSet[String]()
    for (index <- (0 until services.size())) {
      val service = services.get(index)
      namespaces.add(service.getNamespace);

      for(enumIndex <- (0 until service.getEnumDefinitions.size())) {
        val enumDefinition = service.getEnumDefinitions.get(enumIndex);

        namespaces.add(enumDefinition.getNamespace)
      }

      for(structIndex <- (0 until service.getStructDefinitions.size())) {
        val structDefinition = service.getStructDefinitions.get(structIndex);
        structNamespaces.add(structDefinition.getNamespace)
        namespaces.add(structDefinition.getNamespace)
      }
    }

    if(generateAll){
      println("=========================================================")
      toStructArrayBuffer(structs).map{(struct: Struct)=>{

        println(s"生成struct:${struct.name}.java")
        val domainTemplate = new StringTemplate(toDomainTemplate(struct))
        val domainWriter = new PrintWriter(new File(rootDir(outDir, struct.getNamespace), s"${struct.name}.java"), "UTF-8")
        domainWriter.write(domainTemplate.toString)
        domainWriter.close()
        println(s"生成struct:${struct.name}.java 完成")
      }
      }

      toTEnumArrayBuffer(enums).map{(enum: TEnum)=>{

        println(s"生成Enum:${enum.name}.java")
        val enumTemplate = new StringTemplate(toEnumTemplate(enum))
        val enumWriter = new PrintWriter(new File(rootDir(outDir, enum.getNamespace), s"${enum.name}.java"), "UTF-8")
        enumWriter.write(enumTemplate.toString)
        enumWriter.close()
        println(s"生成Enum:${enum.name}.java 完成")
      }
      }
      println("=========================================================")
    }

    for (index <- (0 until services.size())) {

      val service = services.get(index)
      val t1 = System.currentTimeMillis();
      println("=========================================================")
      println(s"服务名称:${service.name}")

      println(s"生成service:${service.name}.java")
      val serviceTemplate = new StringTemplate(toServiceTemplate(service))
      val writer = new PrintWriter(new File(rootDir(outDir, service.getNamespace), s"${service.name}.java"), "UTF-8")
      writer.write(serviceTemplate.toString())
      writer.close()
      println(s"生成service:${service.name}.java 完成")

      println(s"生成AsyncService:${service.name}Async.java")
      val asyncServiceTemplate = new StringTemplate(toAsyncServiceTemplate(service))
      val writer2 = new PrintWriter(new File(rootDir(outDir, service.getNamespace), s"${service.name}Async.java"), "UTF-8")
      writer2.write(asyncServiceTemplate.toString())
      writer2.close()
      println(s"生成AsyncService:${service.name}Async.java 完成")

      if(!generateAll){
        {
          toStructArrayBuffer(service.structDefinitions).map{(struct: Struct)=>{

            println(s"生成struct:${struct.name}.java")
            val domainTemplate = new StringTemplate(toDomainTemplate(struct))
            val domainWriter = new PrintWriter(new File(rootDir(outDir, struct.getNamespace), s"${struct.name}.java"), "UTF-8")
            domainWriter.write(domainTemplate.toString)
            domainWriter.close()
            println(s"生成struct:${struct.name}.java 完成")
          }
          }
        }

        {
          toTEnumArrayBuffer(service.enumDefinitions).map{(enum: TEnum)=>{

            println(s"生成Enum:${enum.name}.java")
            val enumTemplate = new StringTemplate(toEnumTemplate(enum))
            val enumWriter = new PrintWriter(new File(rootDir(outDir, enum.getNamespace), s"${enum.name}.java"), "UTF-8")
            enumWriter.write(enumTemplate.toString)
            enumWriter.close()
            println(s"生成Enum:${enum.name}.java 完成")
          }
          }
        }
      }

      println(s"生成client:${service.name}Client.java")
      val clientTemplate = new StringTemplate(toClientTemplate(service, namespaces))
      val clientWriter = new PrintWriter(new File(rootDir(outDir, service.namespace.substring(0, service.namespace.lastIndexOf("."))), s"${service.name}Client.java"), "UTF-8")
      clientWriter.write(clientTemplate.toString())
      clientWriter.close()
      println(s"生成client:${service.name}Client.java 完成")

      println(s"生成AsyncClient:${service.name}AsyncClient.java")
      val asyncClientTemplate = new StringTemplate(toAsyncClientTemplate(service, namespaces))
      val clientWriter2 = new PrintWriter(new File(rootDir(outDir, service.namespace.substring(0, service.namespace.lastIndexOf("."))), s"${service.name}AsyncClient.java"), "UTF-8")
      clientWriter2.write(asyncClientTemplate.toString())
      clientWriter2.close()
      println(s"生成AsyncClient:${service.name}AsyncClient.java 完成")

      println(s"生成serializer")
      toStructArrayBuffer(service.structDefinitions).map{(struct:Struct)=>{
        val structSerializerTemplate = new StringTemplate(new JavaCodecGenerator().toStructSerializerTemplate(service,struct,structNamespaces))
        val structSerializerWriter = new PrintWriter(new File(rootDir(outDir, struct.namespace+".serializer."),s"${struct.name}Serializer.java"), "UTF-8")
        structSerializerWriter.write(structSerializerTemplate.toString)
        structSerializerWriter.close()
      }}


      println(s"生成Codec:${service.name}Codec.java")
      val codecTemplate = new StringTemplate(new JavaCodecGenerator().toCodecTemplate(service, namespaces,structNamespaces))
      val codecWriter = new PrintWriter(new File(rootDir(outDir, service.namespace.substring(0, service.namespace.lastIndexOf("."))), s"${service.name}Codec.java"), "UTF-8")
      codecWriter.write(codecTemplate.toString())
      codecWriter.close()
      println(s"生成Codec:${service.name}Codec.java 完成")


      println(s"生成AsyncCodec:${service.name}AsyncCodec.java")
      val asyncCodecTemplate = new StringTemplate(new JavaCodecGenerator().toAsyncCodecTemplate(service, namespaces,structNamespaces))
      val asyncCodecWriter = new PrintWriter(new File(rootDir(outDir, service.namespace.substring(0, service.namespace.lastIndexOf("."))), s"${service.name}AsyncCodec.java"), "UTF-8")
      asyncCodecWriter.write(asyncCodecTemplate.toString())
      asyncCodecWriter.close()
      println(s"生成AsyncCodec:${service.name}AsyncCodec.java 完成")


      println(s"生成metadata:${service.namespace}.${service.name}.xml")
      new MetadataGenerator().generateXmlFile(service, resourceDir(outDir, service.namespace.substring(0, service.namespace.lastIndexOf("."))));
      println(s"生成metadata:${service.namespace}.${service.name}.xml 完成")

      println("==========================================================")
      val t2 = System.currentTimeMillis();
      println(s"生成耗时:${t2 - t1}ms")
      println(s"生成状态:完成")

    }

  }

  private def toClientTemplate(service: Service, namespaces:util.Set[String]): Elem = return {
    <div>package {service.namespace.substring(0, service.namespace.lastIndexOf("."))};

      import com.github.dapeng.core.*;
      import com.github.dapeng.org.apache.thrift.*;
      import java.util.ServiceLoader;
      import {service.namespace.substring(0, service.namespace.lastIndexOf(".")) + "." + service.name + "Codec.*"};
      import {service.namespace.substring(0, service.namespace.lastIndexOf(".")) + ".service." + service.name };

      /**
      {notice}
      **/
      public class {service.name}Client implements {service.name}<block>
      private final String serviceName;
      private final String version;

      private SoaConnectionPool pool;
      private final SoaConnectionPool.ClientInfo clientInfo;

      public {service.name}Client() <block>
        this.serviceName = "{service.namespace + "." + service.name }";
        this.version = "{service.meta.version}";

        ServiceLoader{lt}SoaConnectionPoolFactory{gt} factories = ServiceLoader.load(SoaConnectionPoolFactory.class,getClass().getClassLoader());
        this.pool = factories.iterator().next().getPool();
        this.clientInfo = this.pool.registerClientInfo(serviceName,version);
      </block>

      {
      toMethodArrayBuffer(service.methods).map{(method:Method)=>{
        <div>
          <div>
            /**
            * {method.doc}
            **/
            <div>
              public { toDataTypeTemplate(method.getResponse.getFields().get(0).getDataType)} {method.name}({toFieldArrayBuffer(method.getRequest.getFields).map{ (field: Field) =>{
              <div>{toDataTypeTemplate(field.getDataType())} {field.name}{if(field != method.getRequest.fields.get(method.getRequest.fields.size() - 1)) <span>,</span>}</div>}}}) throws SoaException<block>

              String methodName = "{method.name}";

              {method.getRequest.name} {method.getRequest.name} = new {method.getRequest.name}();
              {
              toFieldArrayBuffer(method.getRequest.getFields).map{(field: Field)=>{
                <div>{method.getRequest.name}.set{field.name.charAt(0).toUpper + field.name.substring(1)}({field.name});
                </div>
              }
              }
              }

              {method.response.name} response = pool.send(serviceName,version,"{method.name}",{method.request.name}, new {method.request.name.charAt(0).toUpper + method.request.name.substring(1)}Serializer(), new {method.response.name.charAt(0).toUpper + method.response.name.substring(1)}Serializer());

              {
              toFieldArrayBuffer(method.getResponse.getFields()).map {(field:Field)=> {
                <div>
                  {
                  if(field.getDataType.getKind == DataType.KIND.VOID) {
                    <div></div>
                  } else {
                    <div>
                      return response.getSuccess();
                    </div>
                  }
                  }
                </div>
              }
              }
              }
            </block>
            </div>
          </div>

        </div>
      }
      }
      }

      /**
      * getServiceMetadata
      **/
      public String getServiceMetadata() throws SoaException <block>
        String methodName = "getServiceMetadata";
        getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
        getServiceMetadata_result response = pool.send(serviceName,version,methodName,getServiceMetadata_args, new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
        return response.getSuccess();
      </block>

    </block>
    </div>
  }

  private def toAsyncClientTemplate(service: Service, namespaces:util.Set[String]): Elem = return {
    <div>package {service.namespace.substring(0, service.namespace.lastIndexOf("."))};

      import com.github.dapeng.core.*;
      import com.github.dapeng.org.apache.thrift.*;
      import java.util.concurrent.CompletableFuture;
      import java.util.concurrent.Future;
      import java.util.ServiceLoader;
      import {service.namespace.substring(0, service.namespace.lastIndexOf(".")) + "." + service.name + "AsyncCodec.*"};
      import {service.namespace.substring(0, service.namespace.lastIndexOf(".")) + ".service." + service.name }Async;

      /**
      {notice}
      **/
      public class {service.name}AsyncClient implements {service.name}Async<block>
      private final String serviceName;
      private final String version;

      private SoaConnectionPool pool;
      private final SoaConnectionPool.ClientInfo clientInfo;

      public {service.name}AsyncClient() <block>
        this.serviceName = "{service.namespace + "." + service.name }";
        this.version = "{service.meta.version}";

        ServiceLoader{lt}SoaConnectionPoolFactory{gt} factories = ServiceLoader.load(SoaConnectionPoolFactory.class,getClass().getClassLoader());
        this.pool = factories.iterator().next().getPool();
        this.clientInfo = this.pool.registerClientInfo(serviceName,version);
      </block>

      {
      toMethodArrayBuffer(service.methods).map{(method:Method)=>{
        <div>
          <div>
            /**
            * {method.doc}
            **/
            <div>
              public {if(method.getResponse.getFields().get(0).getDataType.kind.equals(KIND.VOID)) <div>CompletableFuture{lt}Void{gt}</div> else <div>CompletableFuture{lt}{toDataTypeTemplate(method.getResponse.getFields().get(0).getDataType)}{gt}</div>} {method.name}({toFieldArrayBuffer(method.getRequest.getFields).map{ (field: Field) =>{
              <div>{toDataTypeTemplate(field.getDataType())} {field.name}{if(field != method.getRequest.fields.get(method.getRequest.fields.size() - 1)) <span>,</span>}</div>}}}) throws SoaException<block>

              String methodName = "{method.name}";
              {method.getRequest.name} {method.getRequest.name} = new {method.getRequest.name}();
              {
              toFieldArrayBuffer(method.getRequest.getFields).map{(field: Field)=>{
                <div>{method.getRequest.name}.set{field.name.charAt(0).toUpper + field.name.substring(1)}({field.name});
                </div>
              }
              }
              }

              CompletableFuture{lt}{method.response.name}{gt} response = (CompletableFuture{lt}{method.response.name}{gt}) pool.sendAsync(serviceName,version,"{method.name}",{method.request.name}, new {method.request.name.charAt(0).toUpper + method.request.name.substring(1)}Serializer(), new {method.response.name.charAt(0).toUpper + method.response.name.substring(1)}Serializer());

              {
              toFieldArrayBuffer(method.getResponse.getFields()).map {(field:Field)=> {
                <div>
                  {
                  if(field.getDataType.getKind == DataType.KIND.VOID) {
                    <div>return response.thenApply(({method.response.name} result )->  null);</div>
                  } else {
                    <div>
                      return response.thenApply(({method.response.name} result )->  result.getSuccess());
                    </div>
                  }
                  }
                </div>
              }
              }
              }
            </block>
            </div>
          </div>

        </div>
      }
      }
      }

      /**
      * getServiceMetadata
      **/
      public String getServiceMetadata() throws SoaException <block>
        String methodName = "getServiceMetadata";
        getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
        getServiceMetadata_result response = pool.send(serviceName,version,methodName,getServiceMetadata_args, new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
        return response.getSuccess();
      </block>

    </block>
    </div>
  }



  private def toEnumTemplate(enum: TEnum): Elem = {
    return {
      <div>package {enum.namespace};

        /**
        {notice}
        * {enum.doc}
        **/
        public enum {enum.name} implements com.github.dapeng.org.apache.thrift.TEnum<block>
        {
        toEnumItemArrayBuffer(enum.enumItems).map{(enumItem: EnumItem)=>{
          <div>
            /**
            *{enumItem.getDoc}
            **/
            {enumItem.label}({enumItem.value})<div>,</div>
          </div>
        }
        }
        }
        /*
        * 未定义的枚举类型
        */
        UNDEFINED(-1);
        private final int value;

        private {enum.name}(int value)<block>
          this.value = value;
        </block>

        public int getValue()<block>
          return this.value;
        </block>

        public static {enum.name} findByValue(int value)<block>
          switch(value)<block>
            {
            toEnumItemArrayBuffer(enum.enumItems).map{(enumItem: EnumItem)=>{
              <div>
                case {enumItem.value}:
                return {enumItem.label};
              </div>
            }
            }
            }
            case -1:
              return UNDEFINED;
            default:
            return null;
          </block>
        </block>
      </block>
      </div>
    }
  }

  private def toDomainTemplate(struct: Struct): Elem = {
    return {
      <div>package {struct.namespace};

        import java.util.Optional;

        /**
        {notice}
        *{struct.doc}
        **/
        public class {struct.name}<block>
        {toFieldArrayBuffer(struct.getFields).map{(field : Field) =>{
          <div>
            /**
            *{field.doc}
            **/
            {if(field.isPrivacy)  <div>private</div> else <div>public</div>} {if(field.isOptional) <div>Optional{lt}</div>}{toDataTypeTemplate(field.isOptional, field.getDataType)}{if(field.isOptional) <div>{gt}</div>} {field.name} {if(field.isOptional) <div>= Optional.empty()</div> else {
            field.dataType.kind match {
              case KIND.LIST => <div>= new java.util.ArrayList()</div>
              case KIND.SET => <div>= new java.util.HashSet{lt}{gt}()</div>
              case KIND.MAP => <div>= new java.util.HashMap{lt}{gt}()</div>
              case _ => <div></div>
            }
          }};
            public {if(field.isOptional) <div>Optional{lt}</div>}{toDataTypeTemplate(field.isOptional, field.getDataType)}{if(field.isOptional) <div>{gt}</div>} get{field.name.charAt(0).toUpper + field.name.substring(1)}()<block> return this.{field.name}; </block>
            public void set{field.name.charAt(0).toUpper + field.name.substring(1)}({if(field.isOptional) <div>Optional{lt}</div>}{toDataTypeTemplate(field.isOptional, field.getDataType)}{if(field.isOptional) <div>{gt}</div>} {field.name})<block> this.{field.name} = {field.name}; </block>

            public {if(field.isOptional) <div>Optional{lt}</div>}{toDataTypeTemplate(field.isOptional, field.getDataType)}{if(field.isOptional) <div>{gt}</div>} {field.name}()<block> return this.{field.name}; </block>
            public {struct.name} {field.name}({if(field.isOptional) <div>Optional{lt}</div>}{toDataTypeTemplate(field.isOptional, field.getDataType)}{if(field.isOptional) <div>{gt}</div>} {field.name})<block> this.{field.name} = {field.name}; return this; </block>
          </div>
        }
        }
        }

        public String toString()<block>
          StringBuilder stringBuilder = new StringBuilder("<block>");
            {toFieldArrayBuffer(struct.getFields).map{(field : Field) =>{
              getToStringElement(field)
            }
            }
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("</block>");

          return stringBuilder.toString();
        </block>
      </block>
      </div>
    }
  }

  private def toServiceTemplate(service:Service): Elem = return {
    <div>
      package {service.namespace};

      import com.github.dapeng.core.Processor;
      import com.github.dapeng.core.Service;
      import com.github.dapeng.core.SoaGlobalTransactional;

      /**
      {notice}
      * {service.doc}
      **/
      @Service(name="{s"${service.namespace}.${service.name}"}",version = "{service.meta.version}")
      @Processor(className = "{service.namespace.substring(0, service.namespace.lastIndexOf("service"))}{service.name}Codec$Processor")
      {
        if (service.annotations != null) {
          import collection.JavaConverters._

          val methods = classOf[CustomConfig].getDeclaredMethods.map(i => "core." + i.getName -> i.getReturnType.getName).toMap

          val annotationValue = service.annotations.asScala.map(i => {
            if (methods.contains(i.key)) {
                i.key.substring(i.key.lastIndexOf(".") + 1) + "=" + getInstanceTypeValue(i.value, methods.get(i.key).get)
            } else {""}
          }).mkString("(",",",")")
          <div>@com.github.dapeng.core.CustomConfig{annotationValue}</div>
        }
      }
      public interface {service.name} <block>
      {
      toMethodArrayBuffer(service.methods).map { (method: Method) =>
      {
        <div>
          /**
          * {method.doc}
          **/
          {if(method.doc != null && method.doc.contains("@SoaGlobalTransactional")) <div>@SoaGlobalTransactional</div>}
          <div>
            {
            if (method.annotations != null) {
              import collection.JavaConverters._

              val methods = classOf[CustomConfig].getDeclaredMethods.map(i => "core." + i.getName -> i.getReturnType.getName).toMap

              val annotationValue = method.annotations.asScala.map(i => {
                if (methods.contains(i.key)) {
                  i.key.substring(i.key.lastIndexOf(".") + 1) + "=" + getInstanceTypeValue(i.value, methods.get(i.key).get)
                } else {""}
              }).mkString("(",",",")")
              <div>@com.github.dapeng.core.CustomConfig{annotationValue}</div>
            }
            }
            {toDataTypeTemplate(method.getResponse.getFields().get(0).getDataType)} {method.name}({toFieldArrayBuffer(method.getRequest.getFields).map{ (field: Field) =>{
            <div> {toDataTypeTemplate(field.getDataType())} {field.name}{if(field != method.getRequest.fields.get(method.getRequest.fields.size() - 1)) <span>,</span>}</div>}
          }}) throws com.github.dapeng.core.SoaException;
          </div>
        </div>
      }
      }
      }
    </block>
    </div>
  }

  private def toAsyncServiceTemplate(service:Service): Elem = {
    return {
      <div>
        package {service.namespace};

        import com.github.dapeng.core.Processor;
        import com.github.dapeng.core.Service;
        import com.github.dapeng.core.SoaGlobalTransactional;

        import java.util.concurrent.Future;

        /**
        {notice}
        * {service.doc}
        **/
        @Service(name="{s"${service.namespace}.${service.name}"}",version = "{service.meta.version}")
        @Processor(className = "{service.namespace.substring(0, service.namespace.lastIndexOf("service"))}{service.name}AsyncCodec$Processor")
        {
        if (service.annotations != null) {
          import collection.JavaConverters._

          val methods = classOf[CustomConfig].getDeclaredMethods.map(i => "core." + i.getName -> i.getReturnType.getName).toMap

          val annotationValue = service.annotations.asScala.map(i => {
            if (methods.contains(i.key)) {
              i.key.substring(i.key.lastIndexOf(".") + 1) + "=" + getInstanceTypeValue(i.value, methods.get(i.key).get)
            } else {""}
          }).mkString("(",",",")")
          <div>@com.github.dapeng.core.CustomConfig{annotationValue}</div>
        }
        }
        public interface {service.name}Async  extends com.github.dapeng.core.definition.AsyncService <block>
        {
        toMethodArrayBuffer(service.methods).map { (method: Method) =>
        {
          <div>
            /**
            * {method.doc}
            **/
            {if(method.doc != null && method.doc.contains("@SoaGlobalTransactional")) <div>@SoaGlobalTransactional</div>}
            <div>
              {
              if (method.annotations != null) {
                import collection.JavaConverters._

                val methods = classOf[CustomConfig].getDeclaredMethods.map(i => "core." + i.getName -> i.getReturnType.getName).toMap

                val annotationValue = method.annotations.asScala.map(i => {
                  if (methods.contains(i.key)) {
                    i.key.substring(i.key.lastIndexOf(".") + 1) + "=" + getInstanceTypeValue(i.value, methods.get(i.key).get)
                  } else {""}
                }).mkString("(",",",")")
                <div>@com.github.dapeng.core.CustomConfig{annotationValue}</div>
              }
              }
              {if(method.getResponse.getFields().get(0).getDataType.kind.equals(KIND.VOID)) <div>Future{lt}Void{gt}</div> else <div>Future{lt}{toDataTypeTemplate(method.getResponse.getFields().get(0).getDataType)}{gt}</div>} {method.name}({toFieldArrayBuffer(method.getRequest.getFields).map{ (field: Field) =>{
              <div> {toDataTypeTemplate(field.getDataType())} {field.name}{if(field != method.getRequest.fields.get(method.getRequest.fields.size() - 1)) <span>,</span>}</div>}
            }}) throws com.github.dapeng.core.SoaException;
            </div>
          </div>
        }
        }
        }
      </block>
      </div>
    }
  }

  def toDataTypeTemplate(optional: Boolean , dataType:DataType): Elem = {

    if (optional)
      toDataTypeTemplate(dataType)
    else
      dataType.kind match {
        case KIND.BOOLEAN => <div>boolean</div>
        case KIND.SHORT => <div>short</div>
        case KIND.INTEGER => <div>int</div>
        case KIND.LONG => <div>long</div>
        case KIND.DOUBLE => <div>double</div>
        case _ => toDataTypeTemplate(dataType)
      }
  }

  def toDataTypeTemplate(dataType:DataType): Elem = {
    dataType.kind match {
      case KIND.VOID => <div>void</div>
      case KIND.BOOLEAN => <div>Boolean</div>
      case KIND.BYTE => <div>Byte</div>
      case KIND.SHORT => <div>Short</div>
      case KIND.INTEGER => <div>Integer</div>
      case KIND.LONG => <div>Long</div>
      case KIND.DOUBLE => <div>Double</div>
      case KIND.STRING => <div>String</div>
      case KIND.BINARY => <div>java.nio.ByteBuffer</div>
      case KIND.DATE => <div>java.util.Date</div>
      case KIND.BIGDECIMAL => <div>java.math.BigDecimal</div>
      case KIND.MAP =>
        return {<div>java.util.Map{lt}{toDataTypeTemplate(dataType.getKeyType())}, {toDataTypeTemplate(dataType.getValueType())}{gt}</div>}
      case KIND.LIST =>
        return {<div>java.util.List{lt}{toDataTypeTemplate(dataType.getValueType())}{gt}</div>}
      case KIND.SET =>
        return {<div>java.util.Set{lt}{toDataTypeTemplate(dataType.getValueType())}{gt}</div>}
      case KIND.ENUM =>
        val ref = dataType.getQualifiedName();
        return {<div>{ref}</div>}
      case KIND.STRUCT =>
        val ref = dataType.getQualifiedName();
        return {<div>{ref}</div>}
    }
  }

  def getToStringElement(field: Field): Elem = {
    <div>stringBuilder.append("\"").append("{field.name}").append("\":{if(field.dataType.kind == DataType.KIND.STRING) <div>\"</div>}").append({getToStringByDataType(field)}).append("{if(field.dataType.kind == DataType.KIND.STRING) <div>\"</div>},");
    </div>
  }

  def getToStringByDataType(field: Field):Elem = {

    if(field.getDoc != null && field.getDoc.toLowerCase.contains("@logger(level=\"off\")"))
      <div>"LOGGER_LEVEL_OFF"</div>
    else if(field.isOptional)
      <div>this.{field.name}.isPresent()?this.{field.name}.get(){if(field.dataType.kind == KIND.STRUCT) <div>.toString()</div>}:null</div>
    else
      <div>this.{field.name}{if(field.dataType.kind == KIND.STRUCT) <div>.toString()</div>}</div>
  }

  private def getInstanceTypeValue(value: String, typeName: String) = {
      typeName match {
        case "java.lang.String" => s""" "${value}" """
        case "long" =>  value.toLong
        case _ => ""
      }
  }
}