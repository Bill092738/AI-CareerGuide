# Mathematical Explanation of the Point Cloud Map Generation Algorithm

## Warring: This algorithm is incomplete yet!!

This document explains the algorithm for generating a point cloud map in a mathematical way, using a set of target points to influence the movement of points in a 2D plane. The goal is to help collaborators understand and work with the algorithm effectively.

---

## Problem Setup

- **Targets**: Let \( T = \{ t_1, t_2, \ldots, t_n \} \) be a set of \( n \) target points, where each \( t_i \in \mathbb{R}^2 \) represents a fixed position in 2D space.
- **Point Cloud**: Let \( P = \{ p_1, p_2, \ldots, p_m \} \) be a set of \( m \) points, where each \( p_j \in [0, \text{GRID\_SIZE}]^2 \), initially placed randomly or according to some distribution.
- **Convex Hull**: Define \( H \) as the convex hull of \( T \), a convex polygon whose vertices are a subset of \( T \), ordered counter-clockwise.
- **Parameters**:
  - \( \text{MAX\_STEP} \): The maximum distance a point can move in one iteration.
  - \( \text{GRID\_SIZE} \): The size of the square grid where points reside, typically \( [0, \text{GRID\_SIZE}] \times [0, \text{GRID\_SIZE}] \).

The algorithm iteratively updates the positions of points in \( P \) based on their location relative to \( H \) and the lines connecting pairs of targets.

---

## Algorithm Description

For each iteration (e.g., repeated 20 times), the position of every point \( p \in P \) is updated as follows:

### Step 1: Determine if \( p \) is Inside the Convex Hull \( H \)

- **Convex Hull Test**:
  - For \( p \) to be inside \( H \), it must lie to the left of all edges of \( H \) (assuming counter-clockwise orientation).
  - For each edge \( AB \) of \( H \) (where \( A \) and \( B \) are consecutive vertices):
    - Compute the cross product in 2D:
      \[
      \text{cross} = (B - A) \times (p - A) = (B_x - A_x)(p_y - A_y) - (B_y - A_y)(p_x - A_x)
      \]
    - \( p \) is inside \( H \) if \( \text{cross} > 0 \) for all edges \( AB \).
  - If any \( \text{cross} \leq 0 \), then \( p \) is outside \( H \).

### Step 2: Compute the Movement Direction

The direction of movement depends on whether \( p \) is inside or outside \( H \).

#### Case 1: \( p \) is Inside \( H \)
- **Find the Closest Line**:
  - Consider all possible lines \( L_{ij} \) connecting pairs of targets \( t_i \) and \( t_j \) for \( i < j \), where \( L_{ij} = \{ A + t (B - A) \mid t \in \mathbb{R} \} \) and \( A = t_i \), \( B = t_j \).
  - For each line \( L_{ij} \):
    - Vector \( \vec{AB} = B - A \).
    - Distance from \( p \) to \( L_{ij} \):
      \[
      \text{dist}_{ij} = \frac{|\vec{AB} \times (p - A)|}{\|\vec{AB}\|} = \frac{|(B_x - A_x)(p_y - A_y) - (B_y - A_y)(p_x - A_x)|}{\sqrt{(B_x - A_x)^2 + (B_y - A_y)^2}}
      \]
    - Identify the pair \( (i^*, j^*) \) with the smallest \( \text{dist}_{ij} \).
- **Projection onto the Closest Line**:
  - For the closest line \( L_{i^*j^*} \) with endpoints \( A = t_{i^*} \) and \( B = t_{j^*} \):
    - Parameter \( t \):
      \[
      t = \frac{(p - A) \cdot \vec{AB}}{\vec{AB} \cdot \vec{AB}}
      \]
    - Projection point \( Q \):
      \[
      Q = A + t \cdot \vec{AB}
      \]
    - Note: \( t \) is not clamped, so \( Q \) is the projection onto the infinite line, not necessarily the line segment \( [A, B] \).
- **Direction**:
  - \( d = Q - p \), which is perpendicular to \( \vec{AB} \) and points towards the line.

#### Case 2: \( p \) is Outside \( H \)
- **Find the Closest Target**:
  - Compute the Euclidean distance from \( p \) to each target:
    \[
    \text{dist}_k = \| t_k - p \| = \sqrt{(t_{k,x} - p_x)^2 + (t_{k,y} - p_y)^2}, \quad k = 1, 2, \ldots, n
    \]
  - Select the target \( t_{k^*} \) with the minimum \( \text{dist}_k \).
- **Direction**:
  - \( d = t_{k^*} - p \), pointing directly towards the closest target.

### Step 3: Normalize and Scale the Direction
- **Normalization**:
  - Compute the norm: \( \| d \| = \sqrt{d_x^2 + d_y^2} \).
  - If \( \| d \| > 10^{-8} \) (a small threshold to avoid division by zero):
    \[
    d = \frac{d}{\| d \|}
    \]
  - Otherwise, \( d = (0, 0) \).
- **Scaling**:
  - Generate a random scalar \( s \) uniformly from \( [0, \text{MAX\_STEP}] \).
  - Compute the step: \( \delta = s \cdot d \).
- **Clipping**:
  - Clip each component of \( \delta \) to \( [-\text{MAX\_STEP}, \text{MAX\_STEP}] \):
    \[
    \delta_x = \max(-\text{MAX\_STEP}, \min(\text{MAX\_STEP}, \delta_x)), \quad \delta_y = \max(-\text{MAX\_STEP}, \min(\text{MAX\_STEP}, \delta_y))
    \]
  - Note: Since \( \| d \| = 1 \) and \( s \leq \text{MAX\_STEP} \), clipping is typically redundant but ensures safety.

### Step 4: Update Position
- Update \( p \):
  \[
  p \leftarrow p + \delta
  \]
- Clip \( p \) to the grid:
  \[
  p_x = \max(0, \min(\text{GRID\_SIZE}, p_x)), \quad p_y = \max(0, \min(\text{GRID\_SIZE}, p_y))
  \]

### Step 5: Iteration
- Repeat Steps 1–4 for all points in \( P \) for a fixed number of iterations.

---

## Summary of Movement Rules
- **Inside \( H \)**: Points move perpendicularly towards the closest line among all pairs of targets, aiming to cluster near these lines.
- **Outside \( H \)**: Points move directly towards the nearest target, gathering around the target points.

---

## Insights
- **Geometric Interpretation**:
  - Inside the hull, points are attracted to the network of lines, creating linear patterns.
  - Outside the hull, points form clusters around targets.
- **Randomness**: The random step size \( s \) introduces variability, leading to a natural, diffused distribution.
- **Applications**: This can model phenomena like particle attraction, generative art, or spatial simulations.

This mathematical formulation should enable collaborators to understand, implement, or modify the algorithm as needed.