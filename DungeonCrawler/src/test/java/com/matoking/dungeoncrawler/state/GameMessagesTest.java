/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matoking.dungeoncrawler.state;

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
public class GameMessagesTest {
    
    public GameMessagesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAllMethodsReturnValidString() {
        assertEquals(true, GameMessages.getGameLostMessage().length() > 5);
        assertEquals(true, GameMessages.getGameStartMessage().length() > 5);
        assertEquals(true, GameMessages.getGameWonMessage().length() > 5);
        assertEquals(true, GameMessages.getKeyPickupMessage().length() > 5);
        assertEquals(true, GameMessages.getKeysRemainingMessage(0).length() > 5);
        assertEquals(true, GameMessages.getPlayerHitsSkeleton(5).length() > 5);
        assertEquals(true, GameMessages.getPlayerKillsSkeleton(5).length() > 5);
        assertEquals(true, GameMessages.getSkeletonEndsChase().length() > 5);
        assertEquals(true, GameMessages.getSkeletonStartsChase().length() > 5);
        assertEquals(true, GameMessages.getGameRestartMessage().length() > 5);
    }
    
    @Test
    public void testMessagesWithParametersContainParameterValues() {
        assertEquals(true, GameMessages.getKeysRemainingMessage(5).contains("5"));
        assertEquals(true, GameMessages.getPlayerHitsSkeleton(10).contains("10"));
        assertEquals(true, GameMessages.getPlayerKillsSkeleton(55).contains("55"));
    }
    
}
