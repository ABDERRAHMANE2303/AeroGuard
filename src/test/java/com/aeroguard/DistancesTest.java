package com.aeroguard;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.aeroguard.controllers.MainViewController;
import com.aeroguard.infrastructures.Road;
import com.aeroguard.surfaces.Approach;
import com.aeroguard.surfaces.Conique;

public class DistancesTest {

    private MainViewController mainViewController;
    private Road testRoad;
    private Road testRoadBande;
    private Approach approach03Test;
    private Approach approach21Test;
   
    @Before
    public void setUp() {
        // declaring points and getting stuff from the main controller ...not best practice .
        mainViewController = new MainViewController();
        testRoad = mainViewController.getRoad();
        testRoadBande = mainViewController.getRoadBande();
        approach03Test = mainViewController.getApproach03();
        approach21Test = mainViewController.getApproach21();
    }
    

    //basic unit tests for the distances between points , which is always calculated correctly .

    @Test
    public void RoadTest() {
        assertEquals("Échec de la vérification de la largeur de la route", 45, testRoad.getBottomLeftCorner().distanceToPoint(testRoad.getBottomRightCorner()), 15);
        assertEquals("Échec de la vérification de la longueur de la route", 3000, testRoad.getBottomLeftCorner().distanceToPoint(testRoad.getTopLeftCorner()), 15);
        assertEquals("Échec de la vérification de la largeur de la bande de route", 280, testRoadBande.getBottomLeftCorner().distanceToPoint(testRoadBande.getBottomRightCorner()), 15);
        assertEquals("Échec de la vérification de la longueur de la bande de route", 3120, testRoadBande.getBottomLeftCorner().distanceToPoint(testRoadBande.getTopLeftCorner()), 15);
    }

    @Test
    public void TrapezoidSurfacesTest() {
        assertEquals("Échec de la vérification de la longueur de l'Approach03", 15000, approach03Test.getbeginingLine().getCenter().distanceToPoint(approach03Test.getendingLine().getCenter()), 15);
        assertEquals("Échec de la vérification de la longueur de l'Approach21", 15000, approach21Test.getbeginingLine().getCenter().distanceToPoint(approach21Test.getendingLine().getCenter()), 15);
    }   
}
