/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matoking
 */
public class GameLogTest {
    public GameLog gameLog;
    
    public GameLogTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.gameLog = new GameLog(new GameState());
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLogsCreatedCorrectly() {
        this.gameLog.addMessage("Test message One");
        this.gameLog.addMessage("Test message Two");
        
        ArrayList<String> messages = this.gameLog.getMessages();
        
        assertEquals(messages.get(0), "Test message One");
        assertEquals(messages.get(1), "Test message Two");
    }
    
    @Test
    public void testLogsTruncatedCorrectly() {
        for (int i=0; i < 25; i++) {
            this.gameLog.addMessage(String.format("%d", i));
        }
        
        ArrayList<String> messages = this.gameLog.getMessages();
        
        assertEquals(messages.get(0), "5");
        assertEquals(messages.get(19), "24");
    }
    
}
