
              
/*
 * Copyright 2000-2008, Atomikos (http://www.atomikos.com) 
 *
 * This code ("Atomikos TransactionsEssentials"), by itself, 
 * is being distributed under the 
 * Apache License, Version 2.0 ("License"), a copy of which may be found at 
 * http://www.atomikos.com/licenses/apache-license-2.0.txt . 
 * You may not use this file except in compliance with the License. 
 *             
 * While the License grants certain patent license rights, 
 * those patent license rights only extend to the use of 
 * Atomikos TransactionsEssentials by itself. 
 *             
 * This code (Atomikos TransactionsEssentials) contains certain interfaces 
 * in package (namespace) com.atomikos.icatch
 * (including com.atomikos.icatch.Participant) which, if implemented, may
 * infringe one or more patents held by Atomikos.  
 * It should be appreciated that you may NOT implement such interfaces; 
 * licensing to implement these interfaces must be obtained separately from Atomikos.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 */
 
package com.atomikos.icatch.jta.hibernate3;

import com.atomikos.icatch.jta.UserTransactionImp;
import org.hibernate.HibernateException;
import org.hibernate.transaction.JTATransactionFactory;

import javax.transaction.UserTransaction;
import java.util.Properties;

/**
 * Atomikos-specific JTATransactionFactory implementation that does not
 * rely on JNDI for standalone (JNDI-less) deployments.
 *
 * <p>To use Atomikos as the Hibernate JTA transaction manager,
 * specify this class as the value of the 
 * <b>hibernate.transaction.factory_class</b> of the
 * hibernate configuration properties.</p>
 * 
 * 
 * @author Les Hazlewood
 * @author Ludovic Orban
 */
public class AtomikosJTATransactionFactory extends JTATransactionFactory {

    private UserTransaction userTransaction;

    public void configure(Properties props) throws HibernateException {
    	//fix for case 32252: hibernate config init
    	super.configure ( props );
    }

    protected UserTransaction getUserTransaction() {
        if (this.userTransaction == null) {
            this.userTransaction = new UserTransactionImp();
        }
        return this.userTransaction;
    }
}
