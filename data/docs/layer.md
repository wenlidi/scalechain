# Introduction
ScaleChain project has sub-modules with layered architecture. 
Each module exists at a layer and a module can use another module at a layer below it.

# Layers
The followings are list of sub-modules from the top layer to the bottom layer.

+------------------------------------------------+
|           Util          |          Main        |
+------------------------------------------------+
|                   Net                          |
+------------------------------------------------+
|                   Codec|Proto                  |
+------------------------------------------------+
|                   Storage                      |
+------------------------------------------------+
|                   Codec|Block                  |
+------------------------------------------------+
|                   Script                       |
+------------------------------------------------+
|                   Base Layer                   |
+------------------------------------------------+

## Util
A jar package containing all utilities such as DumpChain.

## Main
A jar package containing the ScaleChain main class to run P2P nodes.

## Net
The P2P networking layer.

## Codec-Proto
The codec layer of protocols. Knows how to read/write case classes for P2P networking to/from disk or network.

## Storage
The storage layer. Knows how to store blocks and transactions. It knows how to search blocks by hash or transactions by hash.

## Codec-Block
The codec layer of blocks. Knows how to read/write transaction/block data to/from disk or network.

## Script
The script layer. Implements all bitcoin script commands.

## Base
The base layer. Implements utility functions that can be used by other modules.