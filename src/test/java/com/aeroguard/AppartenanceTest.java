// package com.aeroguard;

// import static org.junit.Assert.assertTrue;

// import org.junit.Before;
// import org.junit.Test;

// import com.aeroguard.controllers.MainViewController;
// import com.aeroguard.geometry.Point;
// import com.aeroguard.infrastructures.Road;
// import com.aeroguard.obstacle.Obstacle;
// import com.aeroguard.surfaces.Approach;
// import com.aeroguard.surfaces.Conique;

// public class AppartenanceTest {
//     private MainViewController mainViewController;
//     private Road testRoad;
//     private Road testRoadBande;
//     private Approach approach03Test;
//     private Approach approach21Test;
//     private Conique coniqueTest;
//     private Point inConique;
//     private Point inHorizentaleinterieur;

//     @Before
//     public void setUp() {
//         // declaring points and getting stuff from the main controller ...
//         mainViewController = new MainViewController();
//         testRoad = mainViewController.getRoad();
//         testRoadBande = mainViewController.getRoadBande();
//         approach03Test = mainViewController.getApproach03();
//         approach21Test = mainViewController.getApproach21();
//         coniqueTest = mainViewController.getConique();

//         // points to test in horizentale interieur o conique
//         Point inHorizentaleinterieur = new Point(23.74463409255077, -15.919674272731584, 0);
//         Point inConique = new Point(23.760723221075875, -15.912807817653459, 0);
//     }

//     @Test
//     public void CircularSurfacesTest() {
//         assertTrue(
//                 coniqueTest.IsInside(new Obstacle(inConique.getLatitude(), inConique.getLongitude(), 0, 0, "test1 ")));
//         // assertTrue(coniqueTest.IsInside(new
//         // Obstacle(inHorizentaleinterieur.getLatitude(),inHorizentaleinterieur.getLongitude()
//         // 0, 0, 0, "test2 ")));
//     }

// }

// // old test , useful values ...

// // @Test
// // public void test() {

// // //Road , RoadBande , OmnidirectionalEquipements

// // Point topLeftRoad = new Point(23.70585, -15.93742, 0);
// // Point topRightRoad = new Point(23.70602, -15.93782, 0);

// // Point topLeftRoadBande = new Point(23.70494,-15.93668, 0);
// // Point topRightRoadBande = new Point(23.70601,-15.93916, 0);

// // Road road = new Road(topLeftRoad, topRightRoad,3000);
// // Road roadBande=new Road(topLeftRoadBande, topRightRoadBande,3120);

// // //Surfaces du cote 03
// // Line surfacesStart03 = new Line(new Point(23.70546,-15.93943,0),new
// // Point(23.70445,-15.93694,0));
// // Approach section103 = new Approach(surfacesStart03.getPointLimite1(),
// // surfacesStart03.getPointLimite2());
// // Section2 section203 = new
// // Section2(section103.Section1LineFinal.getPointLimite1(),
// // section103.Section1LineFinal.getPointLimite2());
// // SectionHorizentale sectionHorizentale03 = new
// // SectionHorizentale(section203.Section2LineFinal.getPointLimite1(),
// // section203.Section2LineFinal.getPointLimite2());
// // AtterissageInterrompu atterissageInterrompu03 = new
// // AtterissageInterrompu(surfacesStart03.getPointLimite1(),
// // surfacesStart03.getPointLimite2());
// // MonteAuDecolage monteAuDecolage03 = new
// // MonteAuDecolage(surfacesStart03.getPointLimite1(),
// // surfacesStart03.getPointLimite2());
// // //surfaces du cote 21
// // Line surfacesStart21 = new Line(new Point(23.73131,-15.92458,0),new
// // Point(23.73236,-15.92707,0));
// // Approach section121 = new Approach(surfacesStart21.getPointLimite1(),
// // surfacesStart21.getPointLimite2());
// // Section2 section221 = new
// // Section2(section121.Section1LineFinal.getPointLimite1(),
// // section121.Section1LineFinal.getPointLimite2());
// // SectionHorizentale sectionHorizentale21 = new
// // SectionHorizentale(section221.Section2LineFinal.getPointLimite1(),
// // section221.Section2LineFinal.getPointLimite2());
// // MonteAuDecolage monteAuDecolage21 = new
// // MonteAuDecolage(surfacesStart21.getPointLimite1(),
// // surfacesStart21.getPointLimite2());

// // Conique conique=new Conique(road.getCenter());
// // HorizentaleInterieur horizentaleInterieur=new
// // HorizentaleInterieur(road.getCenter());

// // Point inS103=new Point(23.750762022395023, -15.916206712916932, 0);
// // Point inS203=new Point(23.780297740117597, -15.909752245143494, 0);
// // Point inSH03=new Point(23.843619801451098, -15.84891545315108, 0);
// // Point inMaD03=new Point(23.732534512743705, -15.925682420924772, 0);

// // Point inS121=new Point(23.688400815123757, -15.948753709987471, 0);
// // Point inS221=new Point(23.656202725153335, -15.953834886745312, 0);
// // Point inSH21=new Point(23.591908619853815, -16.014534349635625, 0);
// // Point inMaD21=new Point(15.973884935573778, -15.973884935573778, 0);

// // Point inHi=new Point(23.74463409255077, -15.919674272731584, 0);
// // Point inC=new Point(23.760723221075875, -15.912807817653459, 0);

// // //array holding the tests
// // ArrayList<Boolean> Appartenance = new ArrayList<>();
// // Appartenance.add(section103.getBase().isPointInside(inS103));
// // Appartenance.add(section203.getBase().isPointInside(inS203));
// // Appartenance.add(sectionHorizentale03.getBase().isPointInside(inSH03));
// // Appartenance.add(monteAuDecolage03.getBase().isPointInside(inMaD03));
// // Appartenance.add(section121.getBase().isPointInside(inS121));
// // Appartenance.add(section221.getBase().isPointInside(inS221));
// // Appartenance.add(sectionHorizentale21.getBase().isPointInside(inSH21));
// // Appartenance.add(monteAuDecolage21.getBase().isPointInside(inMaD21));
// // Appartenance.add(conique.IsInside(inC));
// // Appartenance.add(horizentaleInterieur.IsInside(inHi));
// // //surface names
// // String[] surfaceNames = {
// // "Section103", "Section203", "SectionHorizentale03","Section121",
// // "Section221","SectionHorizentale21",
// // "MonteAuDecolage03","MonteAuDecolage21", "Horizentale interieur", "Conique"
// // };

// // for (int i=0 ; i<10;i++){
// // if(Appartenance.get(i)==true){
// // System.out.println("Correct detection for : " + surfaceNames[i]);
// // }
// // else {
// // System.out.println("Incorrect detection for : " + surfaceNames[i]);
// // }
// // }
// // }
