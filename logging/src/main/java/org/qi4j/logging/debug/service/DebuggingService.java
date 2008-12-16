/*
 * Copyright 2008 Niclas Hedhman. All rights Reserved.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.qi4j.logging.debug.service;

import org.qi4j.api.composite.Composite;
import java.io.Serializable;

public interface DebuggingService
{
    int debugLevel();

    void debug( Composite composite, String message );

    void debug( Composite composite, String message, Serializable param1 );

    void debug( Composite composite, String message, Serializable param1, Serializable param2 );

    void debug( Composite composite, String message, Serializable... params );
}
