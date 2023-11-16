import {
  Box,
  Center,
  Flex,
  Grid,
  GridItem,
  Text,
  Input,
  Stack,
  Button,
  Divider,
} from "@chakra-ui/react";
import Image from "next/image";
import LoginImage from "../assets/sandDuneGuy.jpg";
import Logo from "../assets/Logo.svg";
import { useRouter } from "next/router";

const Login = () => {
  const router = useRouter();
  return (
    <Box height="100vh">
      <Grid templateColumns="repeat(10, 1fr)">
        <GridItem colSpan={6} h="100vh" position="relative">
          <Image src={LoginImage} alt="Image" layout="fill" objectFit="cover" />
        </GridItem>
        <GridItem colSpan={4} h="100vh" width="40vw">
          <Flex
            justifyContent="center"
            alignItems="center"
            h="inherit"
            w="inherit"
            direction="column"
            padding="48px"
            rowGap="48px"
            flexGrow={1}
          >
            <Flex>
              <Image src={Logo} alt="Logo" />
            </Flex>
            <Box>
              <Text
                width="450px"
                fontWeight="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                E-mail
              </Text>
              <Input
                placeholder="Digite seu e-mail"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
              />
            </Box>
            <Box>
              <Text
                width="450px"
                fontWeight="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                Senha
              </Text>
              <Input
                placeholder="Digite sua senha"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
              />
            </Box>
            <Box>
              <Button bgColor="brand.900" w="35vw" textColor="white">
                Entrar
              </Button>
            </Box>
            <Divider color="gray" />
            <Text fontWeight="inter.400" fontSize="sm" display="flex">
              Ainda não tem uma conta?
              <Text
                display="flex"
                color="brand.900"
                marginLeft="10px"
                cursor="pointer"
                onClick={() => router.push("/register")}
              >
                Criar conta
              </Text>
            </Text>
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Login;
