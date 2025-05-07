package com.aeroguard.surfaces.unused.Transitions;
// package com.aeroguard.surfaces.unused.Transition;

// import com.aeroguard.geometry.PyramidLikeSurfaces;
// import com.aeroguard.geometry.Point;
// import com.aeroguard.infrastructures.Road;

// public class Transition {
//     private PyramidLikeSurfaces Front;
//     private PyramidLikeSurfaces Back;
//     private PyramidLikeSurfaces RightSide;
//     private PyramidLikeSurfaces LeftSide;
//     public Transition(Road road) {
//         Point P1=road.getTopLeftCorner();
//         Point P2=road.getTopRightCorner();
//         Point P3=road.getBottomLeftCorner();
//         Point P4=road.getBottomRightCorner();
//         Front=new PyramidLikeSurfaces(P1,P2,0.144,0,315);
//         Back=new PyramidLikeSurfaces(P3,P4,0.144,0,315);
//         RightSide=new PyramidLikeSurfaces(P2,P3,0.144,0,315);
//         LeftSide=new PyramidLikeSurfaces(P1,P4,0.144,0,315);
//     }

//     public PyramidLikeSurfaces getBack() {
//         return Back;
//     }

//     public PyramidLikeSurfaces getFront() {
//         return Front;
//     }

//     public PyramidLikeSurfaces getLeftSide() {
//         return LeftSide;
//     }
//     public PyramidLikeSurfaces getRightSide() {
//         return RightSide;
//     }
// }
