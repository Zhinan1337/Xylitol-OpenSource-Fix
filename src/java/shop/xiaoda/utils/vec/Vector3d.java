//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.vec;

public class Vector3d
{
    private final double x;
    private final double y;
    private final double z;
    
    public Vector3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3d add(final double x, final double y, final double z) {
        return new Vector3d(this.x + x, this.y + y, this.z + z);
    }
    
    public Vector3d add(final Vector3d vector) {
        return this.add(vector.x, vector.y, vector.z);
    }
    
    public Vector3d subtract(final double x, final double y, final double z) {
        return this.add(-x, -y, -z);
    }
    
    public Vector3d subtract(final Vector3d vector) {
        return this.add(-vector.x, -vector.y, -vector.z);
    }
    
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public Vector3d multiply(final double v) {
        return new Vector3d(this.x * v, this.y * v, this.z * v);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Vector3d)) {
            return false;
        }
        final Vector3d vector = (Vector3d)obj;
        return Math.floor(this.x) == Math.floor(vector.x) && Math.floor(this.y) == Math.floor(vector.y) && Math.floor(this.z) == Math.floor(vector.z);
    }
}
