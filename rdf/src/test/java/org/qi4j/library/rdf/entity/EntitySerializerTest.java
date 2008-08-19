/*
 * Copyright (c) 2008, Rickard Öberg. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.qi4j.library.rdf.entity;

import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandlerException;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entity.EntityBuilder;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.qi4j.injection.scope.Service;
import org.qi4j.library.rdf.DcRdf;
import org.qi4j.library.rdf.Rdfs;
import org.qi4j.library.rdf.serializer.RdfXmlSerializer;
import org.qi4j.spi.entity.EntityDescriptor;
import org.qi4j.spi.entity.EntityState;
import org.qi4j.spi.entity.EntityStore;
import org.qi4j.spi.entity.QualifiedIdentity;
import org.qi4j.test.AbstractQi4jTest;

/**
 * TODO
 */
public class EntitySerializerTest
    extends AbstractQi4jTest
{
    @Service EntityStore entityStore;
    @Service EntitySerializer serializer;

    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addServices( MemoryEntityStoreService.class, EntitySerializerService.class );
        module.addEntities( TestEntity.class );
        module.addObjects( EntitySerializerTest.class );
    }

    @Override @Before public void setUp() throws Exception
    {
        super.setUp();

        createDummyData();

        objectBuilderFactory.newObjectBuilder( EntitySerializerTest.class ).injectTo( this );
    }

    @Test
    public void testEntitySerializer() throws RDFHandlerException
    {
        QualifiedIdentity qualifiedIdentity = new QualifiedIdentity( "test2", TestEntity.class );
        EntityState entityState = entityStore.getEntityState( qualifiedIdentity );

        Iterable<Statement> graph = serializer.serialize( entityState );

        String[] prefixes = new String[]{ "rdf", "dc", " vc" };
        String[] namespaces = new String[]{ Rdfs.RDF, DcRdf.DC, "http://www.w3.org/2001/vcard-rdf/3.0#" };


        new RdfXmlSerializer().serialize( graph, new PrintWriter( System.out ), prefixes, namespaces );
    }

    @Test
    public void testEntityTypeSerializer() throws RDFHandlerException
    {

        EntityDescriptor entityDescriptor = spi.getEntityDescriptor( TestEntity.class, moduleInstance );

        Iterable<Statement> graph = serializer.serialize( entityDescriptor.entityType() );

        String[] prefixes = new String[]{ "rdf", "dc", " vc" };
        String[] namespaces = new String[]{ Rdfs.RDF, DcRdf.DC, "http://www.w3.org/2001/vcard-rdf/3.0#" };


        new RdfXmlSerializer().serialize( graph, new PrintWriter( System.out ), prefixes, namespaces );
    }

    void createDummyData()
        throws Exception
    {
        UnitOfWork unitOfWork = unitOfWorkFactory.newUnitOfWork();
        try
        {
            EntityBuilder<TestEntity> builder = unitOfWork.newEntityBuilder( "test1", TestEntity.class );
            TestEntity rickardTemplate = builder.stateOfComposite();
            rickardTemplate.name().set( "Rickard" );
            rickardTemplate.title().set( "Mr" );
            TestEntity testEntity = builder.newInstance();

            EntityBuilder<TestEntity> builder2 = unitOfWork.newEntityBuilder( "test2", TestEntity.class );
            TestEntity niclasTemplate = builder2.stateOfComposite();
            niclasTemplate.name().set( "Niclas" );
            niclasTemplate.title().set( "Mr" );
            niclasTemplate.association().set( testEntity );
            niclasTemplate.manyAssoc().add( testEntity );
            niclasTemplate.group().add( testEntity );
            niclasTemplate.group().add( testEntity );
            niclasTemplate.group().add( testEntity );
            TestEntity testEntity2 = builder2.newInstance();
            unitOfWork.complete();
        }
        catch( Exception e )
        {
            unitOfWork.discard();
            throw e;
        }

    }
}


