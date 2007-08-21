/*
 * Copyright 2003-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.groovy.tools.shell

/**
 * A registry of shell {@link Command} instances which may be executed.
 *
 * @version $Id$
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 */
class CommandRegistry
{
    final List commands = []

    private final Map nameMap = [:]
    
    void register(final Command command) {
        assert command

        // Make sure that the command name and shortcut are unique
        assert !nameMap.containsKey(command.name) : "Duplicate comamnd name: $command.name"
        nameMap[command.name] = command
        
        assert !nameMap.containsKey(command.shortcut) : "Duplicate command shortcut: $command.shortcut"
        nameMap[command.shortcut] = command

        // Hold on to the command in order
        commands << command
        
        // Hookup context for alias commands
        command.registry = this
    }

    def leftShift(final Command command) {
        register(command)
    }
    
    Command find(final String name) {
        assert name
        
        for (c in commands) {
            if (name in [ c.name, c.shortcut ]) {
                return c
            }
        }
        
        return null
    }

    List commands() {
        return commands
    }
    
    def getProperty(final String name) {
        return find(name)
    }
}