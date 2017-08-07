<template>
  <footer class="footer" v-once>Copyright &copy; {{getYear}} 首约科技（北京）有限公司 Inc. All rights reserved. </footer>
</template>
<script type="text/javascript">
  export default {
    computed: {
      getYear(){
        return new Date().getFullYear()
      }
    }
  }
</script>
