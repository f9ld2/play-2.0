// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : Globalクラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.02.04 H.Okuhara 新規作成
 * 
 * ===================================================================
 */
import java.lang.reflect.Method;
import java.util.Properties;

import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.biz.mg.MgPluConverterImpl;
import jp.co.necsoft.web_md.common.util.InjectorHolder;

import org.mybatis.guice.XMLMyBatisModule;

import play.Application;
import play.Configuration;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.SimpleResult;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Global 
 */
public class Global extends GlobalSettings {

    private Injector injector;

    @Override
    public void onStart(Application app) {
        final Properties props = new Properties();
        final Configuration config = app.configuration();
        props.setProperty("driver", config.getString("db.default.driver"));
        props.setProperty("url", config.getString("db.default.url"));
        props.setProperty("username", config.getString("db.default.user"));
        props.setProperty("password", config.getString("db.default.password"));

        final InjectorHolder holder = new InjectorHolder();
        injector = Guice.createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                setEnvironmentId("default");
                addProperties(props);
                setClassPathResource("mybatis-config.xml");
                // bindDataSourceProviderType(BoneCPProvider.class);

                // bind(MemberService.class).to(MemberServiceImpl.class);
            }
        },
        new AbstractModule() {
            @Override
            protected void configure() {
                bind(InjectorHolder.class).toInstance(holder);
                bind(IPluConverter.class).to(MgPluConverterImpl.class);
            }
        });

    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) throws Exception {
        return injector.getInstance(clazz);
    }

    @Override
    public Action<?> onRequest(Request request, Method actionMethod) {
        return new Action.Simple() {
            public Promise<SimpleResult> call(Context ctx) throws Throwable {
                /* your code here */
                ctx.response().setHeader("Cache-Control", "max-age=0");
                return (Promise<SimpleResult>) delegate.call(ctx);
            }
        };
    }

}