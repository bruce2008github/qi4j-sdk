/*
 * Copyright (C) Senselogic 2006, all rights reserved
 */
package org.qi4j.test.model;

import org.qi4j.api.annotation.Modifies;

/**
 * TODO
 *
 * @author rickard
 * @version $Revision: 1.7 $
 */
public class StandardThatModifier
    implements StandardThat
{
    // Attributes ----------------------------------------------------
    @Modifies StandardThat next;

    // Z implementation ----------------------------------------------
    public String foo( String aString )
    {
        return next.foo( aString);
    }

    public String test( String aString )
    {
        return next.test( aString);
    }
}
