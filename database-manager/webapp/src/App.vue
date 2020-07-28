<template>
    <div id="app">
        <div id="nav">

        </div>
        <router-view/>

    </div>
</template>

<style lang="scss">

    *{
        box-sizing: border-box;
        border: 0;
        margin: 0;
        padding: 0;
    }
    .icon {
        width: 1em;
        height: 1em;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #nav {


        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }
</style>
<script>
    export default {

    }
</script>
