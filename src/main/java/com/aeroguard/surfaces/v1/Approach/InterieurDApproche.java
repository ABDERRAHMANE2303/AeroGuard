// package com.aeroguard.surfaces.unused.Approach;

// import com.aeroguard.geometry.IsocelTrapezoid;
// import com.aeroguard.geometry.PyramidLikeSurfaces;
// import com.aeroguard.geometry.Point;

// public class InterieurDApproche extends PyramidLikeSurfaces {
//     private IsocelTrapezoid base;

//     public InterieurDApproche(Point topLeft, Point topRight) {
//         super(topLeft, topRight, 0.02, 0, 900);
//         if (topLeft == null || topRight == null) {
//             throw new IllegalArgumentException("Top left and top right points cannot be null");
//         }
//         this.base = new IsocelTrapezoid(topLeft, topRight, 900, 0.0);
//     }
// }
